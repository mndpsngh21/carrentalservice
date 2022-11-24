package com.mandeep.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mandeep.carrental.entities.ChargesEntity;

@Repository
public interface ChargesRepository extends JpaRepository<ChargesEntity, Long>{

}
