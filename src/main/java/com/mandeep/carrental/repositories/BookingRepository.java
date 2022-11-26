package com.mandeep.carrental.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mandeep.carrental.entities.VehicleBookings;

@Repository
public interface BookingRepository  extends JpaRepository<VehicleBookings, Long>{

	@Query("Select b from VehicleBookings b where b.bookingFrom between :from and :till or b.bookingTill between :from and :till")
	List<VehicleBookings> getBookingForDateRange(@Param("from")LocalDateTime from, @Param("till")LocalDateTime till);
	
	Optional<VehicleBookings> findByBookingId(String bookingId);

	List<VehicleBookings> findByBookingStatus(String string);

	@Query("Select b from VehicleBookings b where b.accountId=:user_id and b.bookingFrom between :from and :till or b.bookingTill between :from and :till")
	List<VehicleBookings> findByAccountIdBetweenDate(String user_id, LocalDateTime from, LocalDateTime till);

}
