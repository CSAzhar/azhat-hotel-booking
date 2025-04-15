package com.azhar.hotel.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(content = Include.NON_NULL)
public class Response {
	
	
	private int statusCode;
	private String message;
	
	private String token;
	private String role;
	private String expirationTime;
	private String bookingConfirmationCode;
	
	private UserDTO user;
	private RoomDTO room;
	private BookingDTO booking;
	
	private List<UserDTO> userList;
	private List<RoomDTO> roomList;
	private List<BookingDTO> bookingList;

}













