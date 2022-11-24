package com.mandeep.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.entities.VehicleTypeEntity;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long>{

}
