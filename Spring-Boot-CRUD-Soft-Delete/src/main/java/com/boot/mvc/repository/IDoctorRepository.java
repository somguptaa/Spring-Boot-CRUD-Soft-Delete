package com.boot.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.mvc.entity.DoctorEntity;

public interface IDoctorRepository extends JpaRepository<DoctorEntity,Integer> {

}
