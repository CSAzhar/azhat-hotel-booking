package com.azhar.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azhar.hotel.dto.Response;
import com.azhar.hotel.entity.Booking;
import com.azhar.hotel.service.interf.IBookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {
	
	@Autowired
	private IBookingService bookingService;
	
	@PostMapping("/book-room/{roomId}/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> saveBooking(@PathVariable Long roomId,
												@PathVariable Long userId,
												@RequestBody Booking booking
												){
		Response response = bookingService.saveBooking(roomId, userId, booking);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllBookings(){
		Response response = bookingService.getAllBookings();
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@GetMapping("/get-by-confirmation-code/{confirmationCode}")
	public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
		Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@DeleteMapping("/cancel/{bookingId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> cancelBookin(@PathVariable Long bookingId){
		Response response = bookingService.cancelBooking(bookingId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}












