package com.azhar.hotel.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.azhar.hotel.dto.Response;
import com.azhar.hotel.service.interf.IRoomService;

@RestController
@RequestMapping("/rooms")
@CrossOrigin
public class RoomController {
	
	@Autowired
	private IRoomService roomService;
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addnewRoom(
												@RequestParam(value = "photo", required = false) MultipartFile photo,
												@RequestParam(value = "roomType", required = false) String roomType,
												@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
												@RequestParam(value = "roomDescription", required = false) String roomDescription
												) {
		
		if(photo == null || roomType == null || roomType.isBlank() || roomPrice == null || roomType.isBlank()) {
			Response response = new Response();
			response.setStatusCode(400);
			response.setMessage("Please Provide value for all the fields(photo, roomPrice, roomType)");
			return ResponseEntity.status(response.getStatusCode()).body(response);
		}
		Response response = roomService.addNewRoom(photo, roomType, roomPrice, roomDescription);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<Response> getAllRooms(){
		Response response = roomService.getAllRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/types")
	public List<String> getRoomType(){
		return roomService.getAllRoomTypes();
	}
	
	@GetMapping("/get-room-by-id/{roomId}")
	public ResponseEntity<Response> getRoomById(@PathVariable("roomId") Long roomId){
		Response response = roomService.getRoomById(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-all-available-rooms")
	public ResponseEntity<Response> getAllAvailableRooms(){
		Response response = roomService.getAllAvailableRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/available-rooms-by-date-and-type")
	public ResponseEntity<Response> getAvailableRoomsBydataAndType(
													@RequestParam(value = "checkInDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate checkInDate,
													@RequestParam(value = "checkOutDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate checkOutDate,
													@RequestParam(value = "roomType", required = false) String roomType
																){
		if( checkInDate==null || checkOutDate==null || roomType==null || roomType.isBlank()) {
			Response response = new Response();
			response.setStatusCode(400);
			response.setMessage("Please provide the details(checkInDate, checkOutdate and roomType)");
			return ResponseEntity.status(response.getStatusCode()).body(response);
		}
		Response response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@PutMapping("/update-room/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateRoomById(
													@PathVariable("roomId") Long roomId,
													@RequestParam(value = "photo", required = false) MultipartFile photo,
													@RequestParam(value = "roomType", required = false) String roomType,
													@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
													@RequestParam(value = "roomDescription", required = false) String roomDescription
													){
		Response response = roomService.updateRoomById(roomId, roomType, roomPrice, roomDescription, photo);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}

	@DeleteMapping("/delete-room/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteRoomById(@PathVariable("roomId") Long roomId){
		Response response = roomService.deleteRoomById(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}




