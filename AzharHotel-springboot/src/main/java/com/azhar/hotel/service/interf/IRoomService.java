package com.azhar.hotel.service.interf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.azhar.hotel.dto.Response;

public interface IRoomService {
	
	Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);
	
	List<String> getAllRoomTypes();
	
	Response getAllRooms();
	
	Response deleteRoomById(Long roomId);
	
	Response updateRoomById(Long roomId, String roomType, BigDecimal roomPrice, String roomDescription, MultipartFile photo);
	
	Response getRoomById(Long roomId);
	
	Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
	
	Response getAllAvailableRooms();
	

}













