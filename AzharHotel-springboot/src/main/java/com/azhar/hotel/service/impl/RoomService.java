package com.azhar.hotel.service.impl;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azhar.hotel.dto.Response;
import com.azhar.hotel.dto.RoomDTO;
import com.azhar.hotel.entity.Room;
import com.azhar.hotel.exception.OurException;
import com.azhar.hotel.repo.RoomRepository;
import com.azhar.hotel.service.AwsS3Service;
import com.azhar.hotel.service.interf.IRoomService;
import com.azhar.hotel.utils.Utils;

@Service
public class RoomService implements IRoomService {

	@Autowired
	private RoomRepository roomRepository;
//	@Autowired
//	private BookingRepository bookingRepository;
	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
		Response response = new Response();
		try {
			String imageUrl = awsS3Service.saveImageToS3(photo);
			Room room = new Room();
			room.setRoomPhotoUrl(imageUrl);
			room.setRoomType(roomType);
			room.setRoomPrice(roomPrice);
			room.setRoomDescription(description);

			Room savedRoom = roomRepository.save(room);

			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

			response.setRoom(roomDTO);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (Exception e) {
			response.setStatusCode(0);
			response.setMessage("");
		}
		return response;
	}

	@Override
	public List<String> getAllRoomTypes() {
		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public Response getAllRooms() {

		Response response = new Response();

		try {
			List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			if (roomList == null) {
				throw new OurException("no rooma available");
			}
			List<RoomDTO> roomListDTO = Utils.mapRoomListEntityToRoomListDTO(roomList);

			response.setRoomList(roomListDTO);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Something occured while fetching rooms" + e.getMessage());
		}

		return response;
	}

	@Override
	public Response deleteRoomById(Long roomId) {
		Response response = new Response();

		try {
			roomRepository.findById(roomId).orElseThrow(() -> new OurException("room not found"));
			roomRepository.deleteById(roomId);
			response.setStatusCode(200);
			response.setMessage("successful");
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("deletetion of room failed " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateRoomById(Long roomId, String roomType, BigDecimal roomPrice, String roomDescription,
			MultipartFile photo) {

		Response response = new Response();

		try {
			String roomURL = null;
			if (photo != null && !photo.isEmpty()) {
				roomURL = awsS3Service.saveImageToS3(photo);
			}
			Room roomToUpdate = roomRepository.findById(roomId).orElseThrow(() -> new OurException("room not found"));

			if (roomType != null)
				roomToUpdate.setRoomType(roomType);
			if (roomPrice != null)
				roomToUpdate.setRoomPrice(roomPrice);
			if (roomURL != null)
				roomToUpdate.setRoomPhotoUrl(roomURL);
			if (roomDescription != null)
				roomToUpdate.setRoomDescription(roomDescription);

			Room savedRoom = roomRepository.save(roomToUpdate);

			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

			response.setRoom(roomDTO);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("something occured while updating room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getRoomById(Long roomId) {
		Response response = new Response();

		try {
			Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("room not found"));
			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(room);

			response.setRoom(roomDTO);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("something went wrong while finding room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		
		Response response = new Response();

		try {
			List<Room> roomList = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

			response.setRoomList(roomDTOList);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("something went wrong while finding room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllAvailableRooms() {
		Response response = new Response();

		try {
			List<Room> availableRooms = roomRepository.getAllAvailableRooms();
			if (availableRooms == null) {
				throw new OurException("no rooma available");
			}
			List<RoomDTO> availableRoomDTO = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

			response.setRoomList(availableRoomDTO);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Something occured while fetching rooms" + e.getMessage());
		}

		return response;
	}

}
