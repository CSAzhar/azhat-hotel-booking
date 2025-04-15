package com.azhar.hotel.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(content = Include.NON_NULL)
public class UserDTO {
	
	private Long id;
	private String email;
	private String name;
	private String phoneNumber;
	private String role;
	private List<BookingDTO> bookings = new ArrayList<>();
	

}
