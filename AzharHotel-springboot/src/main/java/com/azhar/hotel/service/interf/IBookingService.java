package com.azhar.hotel.service.interf;

import com.azhar.hotel.dto.Response;
import com.azhar.hotel.entity.Booking;

public interface IBookingService {
	
	Response saveBooking(Long roomId, Long userId, Booking bookingRequest);
	
	Response findBookingByConfirmationCode(String confirmationCode);
	
	Response getAllBookings();
	
	Response cancelBooking(Long bookingId);
	

}
