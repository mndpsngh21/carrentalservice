package com.mandeep.carrental.utils;

import com.mandeep.carrental.models.Location;

public class Constants {

	public class ResponseCode{

		public static final int DEFAULT_SUCCESS = 200;
		public static final int DUPLICATE_RECORD = 422;
		public static final int NOT_FOUND = 404;
		public static final int INVALID_REQUEST = 400;
	
	}
	public class ResponseMessages{

		public static final String SAVED_PARKING = "Parking saved successfully.";
		public static final String FAILED_SAVED_PARKING = "Failed to save Parking";
		public static final String DEFAULT = "Success";
		public static final String DELETE_SUCCESS = "Record deleted successfully";
		public static final String BOOKED_SUCCESS = "Booked successfully";
		public static final String BOOKING_CONFIRMED = "Booking confirmed";
		
	}
	
	public class VehicleModel{
		public static final String BMW_650 = "BMW 650";
		public static final String Toyota_Camry = "Toyota Camry";
	}
	
	public static class Parkings{
		public static final Location location_1= new Location(12.458, 72.4545);
		public static final Location location_2= new Location(12.1358, 72.564545);
		public static final Location location_3= new Location(12.7688, 72.874545);
		
		
	}

	public class TimeFormat{

		public static final String IST_DATETIME = "yyyy-MM-dd HH:mm:ss";
		
	}

}
