package com.azhar.hotel.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;

import com.azhar.hotel.dto.BookingDTO;
import com.azhar.hotel.dto.RoomDTO;
import com.azhar.hotel.dto.UserDTO;
import com.azhar.hotel.entity.Booking;
import com.azhar.hotel.entity.Room;
import com.azhar.hotel.entity.User;

import java.util.List;


public class Utils {
	
	private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private static final SecureRandom secureRandom = new SecureRandom();
	
	public static String generateConfirmationCode(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<length; i++) {
			int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
			char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
			stringBuilder.append(randomChar);
		}
		return stringBuilder.toString();
	}
	
	
	public static UserDTO mapUserEntityToUserDto(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setRole(user.getRole());
		
		return userDTO;
		
	}
	
	public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
		RoomDTO roomDTO = new RoomDTO();
		
		roomDTO.setId(room.getId());
		roomDTO.setRoomType(room.getRoomType());
		roomDTO.setRoomPrice(room.getRoomPrice());
		roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDTO.setRoomDescription(room.getRoomDescription());
		
		return roomDTO;
	}
	
	public static BookingDTO mapBookingEntityTobookingDTO(Booking booking) {
		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setId(booking.getId());
		bookingDTO.setCheckInDate(booking.getCheckInDate());
		bookingDTO.setCheckOutdate(booking.getCheckOutDate());
		bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
		bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
		bookingDTO.setTotalNumberOfGuests(booking.getTotalNumberOfGuests());
		
		return bookingDTO;
	}
	
	public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room) {
		RoomDTO roomDTO = new RoomDTO();
		
		roomDTO.setId(room.getId());
		roomDTO.setRoomType(room.getRoomType());
		roomDTO.setRoomPrice(room.getRoomPrice());
		roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDTO.setRoomDescription(room.getRoomDescription());
		
		if(room.getBookings() != null) {
			roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityTobookingDTO).collect(Collectors.toList()));
		}
		
		return roomDTO;
	}
	
	
	
	public static UserDTO mapUserEntityToUserDtoPlusUserBookingAndRoom(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setRole(user.getRole());
		
		if(!user.getBookings().isEmpty()) {
			userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedRoom(booking, false)).collect(Collectors.toList()));
		}
		
		return userDTO;
		
	}
	
	public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRoom(Booking booking, boolean mapUser) {
		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setId(booking.getId());
		bookingDTO.setCheckInDate(booking.getCheckInDate());
		bookingDTO.setCheckOutdate(booking.getCheckOutDate());
		bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
		bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
		bookingDTO.setTotalNumberOfGuests(booking.getTotalNumberOfGuests());
		
		if(mapUser == true) {
			bookingDTO.setUser(Utils.mapUserEntityToUserDto(booking.getUser()));
		}
		if(booking.getRoom() != null) {
			RoomDTO roomDTO = new RoomDTO();
			roomDTO.setId(booking.getRoom().getId());
			roomDTO.setRoomType(booking.getRoom().getRoomType());
			roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
			roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
			roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
			bookingDTO.setRoom(roomDTO);
		}
		
		return bookingDTO;
		
	}
	
	public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
		return userList.stream().map(Utils::mapUserEntityToUserDto).collect(Collectors.toList());
	}
	
	public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList){
		return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
	}
	
	public static List<BookingDTO> mapBookingListToBookingListDTO(List<Booking> bookingList){
		return bookingList.stream().map(Utils::mapBookingEntityTobookingDTO).collect(Collectors.toList());
	}
	
	
	
	
	

}



















