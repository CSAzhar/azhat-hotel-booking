package com.azhar.hotel.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azhar.hotel.dto.BookingDTO;
import com.azhar.hotel.dto.Response;
import com.azhar.hotel.entity.Booking;
import com.azhar.hotel.entity.Room;
import com.azhar.hotel.entity.User;
import com.azhar.hotel.exception.OurException;
import com.azhar.hotel.repo.BookingRepository;
import com.azhar.hotel.repo.RoomRepository;
import com.azhar.hotel.repo.UserRepository;
import com.azhar.hotel.service.interf.IBookingService;
import com.azhar.hotel.utils.Utils;

@Service
public class BookingService implements IBookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private RoomService roomService;
	

	@Override
	public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
		Response response = new Response();
		
		try {
			
			if(bookingRequest.getCheckInDate().isBefore(LocalDate.now())) {
				throw new IllegalArgumentException("please choose date after now");
			}
			
			if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("checkout should be after checkin");
			}
			
			Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("room not found"));
			User user = userRepository.findById(userId).orElseThrow(()->new OurException("user not found"));
			
			List<Booking> existingBookings = room.getBookings();
			
			if(!roomIsAvailable(bookingRequest, existingBookings)) {
				throw new OurException("room not available for selected range");
			}
			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			String bookingConfirmationCode = Utils.generateConfirmationCode(10);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			bookingRepository.save(bookingRequest);
			
			response.setStatusCode(200);
			response.setBookingConfirmationCode(bookingConfirmationCode);
			response.setMessage("successful");
			
			
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("error occured while booking "+e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response findBookingByConfirmationCode(String confirmationCode) {
		Response response = new Response();
		
		try {
			
			Booking searchBooking = bookingRepository
								.findByBookingConfirmationCode(confirmationCode).orElseThrow( ()-> new OurException("booking not found") );
			BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRoom(searchBooking, true);
			
			response.setBooking(bookingDTO);
			response.setStatusCode(200);
			response.setMessage("successful");
			
			
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("error while searching booking "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllBookings() {
		Response response = new Response();
		
		try {
			
			List<Booking> bookingList = bookingRepository.findAll();
			if(bookingList == null) throw new OurException("no bookings available");
			
			List<BookingDTO> bookingDTOList = Utils.mapBookingListToBookingListDTO(bookingList);
			
			response.setBookingList(bookingDTOList);
			response.setStatusCode(200);
			response.setMessage("successful");
			
			
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("error while fetching booking "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response cancelBooking(Long bookingId) {
		Response response = new Response();
		
		try {
			
			bookingRepository.findById(bookingId).orElseThrow( ()-> new OurException("no bboking found") );
			bookingRepository.deleteById(bookingId);
			response.setStatusCode(200);
            response.setMessage("successful");
			
			
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("error while deleting booking "+e.getMessage());
		}
		return response;
	}
	
	
	private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
		return existingBookings.stream()
				.noneMatch(existingBooking -> 
				
						   bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
						
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
						
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
						
						);
	}
	
	
	

}
