package com.azhar.hotel.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(content = Include.NON_NULL)
public class BookingDTO {
	
	private Long id;
	private LocalDate checkInDate;
	private LocalDate checkOutdate;
	private int numberOfAdults;
	private int numberOfChildren;
	private int totalNumberOfGuests;
	private String bookingConfirmationCode;
	private UserDTO user;
	private RoomDTO room;
	
	

}
