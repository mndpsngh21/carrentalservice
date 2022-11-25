package com.mandeep.carrental.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mandeep.carrental.entities.ParkingEntity;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

	Optional<ParkingEntity> findByUuid(String id);
	
	@Query("Select p from ParkingEntity p where p.latitude=:parkingLatitude and p.longitude=:parkingLongitude")
	Optional<ParkingEntity> findByLocation(double parkingLatitude, double parkingLongitude);

}
