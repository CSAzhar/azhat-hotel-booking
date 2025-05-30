package com.azhar.hotel.service.interf;

import com.azhar.hotel.dto.LoginRequest;

import com.azhar.hotel.dto.Response;
import com.azhar.hotel.entity.User;

public interface IUserService {
	
	Response register(User user);
	
	Response login(LoginRequest loginRequest);
	
	Response getAllUsers();
	
	Response getUserBookingHistory(String userId);
	
	Response deleteUser(String UserId);
	
	Response getUserById(String userId);
	
	Response getMyInfo(String email);

}
