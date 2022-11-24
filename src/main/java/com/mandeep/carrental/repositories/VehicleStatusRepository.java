package com.mandeep.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mandeep.carrental.entities.VehiceStatusEntity;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehiceStatusEntity, Long>{

}
