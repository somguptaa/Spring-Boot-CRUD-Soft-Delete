package com.boot.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.mvc.dto.DoctorDto;
import com.boot.mvc.entity.DoctorEntity;
import com.boot.mvc.exception.DoctorNotFoundException;
import com.boot.mvc.repository.IDoctorRepository;

@Service  
// Marks this class as Service Layer component
// Business logic should be written here
public class DoctorMgmtServiceImpl implements IDoctorMgmtService {

	@Autowired
    // Injecting Repository layer (DAO layer)
    private IDoctorRepository doctorRepo;

    // Constant GST Rate (18%)
    private static final double GST_RATE = 0.18;


    // ========================= SAVE DOCTOR =========================
    @Override
    public String saveDoctor(DoctorDto docdto) {
        
        // Convert DTO to Entity
        DoctorEntity entity = new DoctorEntity();
        BeanUtils.copyProperties(docdto, entity);

        // Save entity to database
        // save() returns saved entity with generated ID
        int idVal = doctorRepo.save(entity).getId();

        return "Doctor is saved with id Value :: " + idVal;
    }


    // ========================= FETCH ALL DOCTORS =========================
    @Override
    public List<DoctorDto> showAllDoctors() {

        // Fetch all records from DB
        List<DoctorEntity> listEntity = doctorRepo.findAll();

        // Prepare DTO list
        List<DoctorDto> listDto = new ArrayList<>();

        // Convert each Entity to DTO
        listEntity.forEach(entity -> {

            DoctorDto docdto = new DoctorDto();

            // Copy DB data to DTO
            BeanUtils.copyProperties(entity, docdto);

            // Business Logic:
            // Calculate Net Fee including GST
            double fee = docdto.getFee();
            docdto.setNetfee(fee + (fee * GST_RATE));

            listDto.add(docdto);
        });

        return listDto;
    }


    // ========================= FETCH BY ID =========================
    @Override
    public DoctorDto showDoctorById(int id) {

        // findById() returns Optional
        // If not found → throw custom exception
        DoctorEntity entity = doctorRepo.findById(id)
                .orElseThrow(() -> 
                   new DoctorNotFoundException("Invalid Doctor Id: " + id));

        // Convert Entity to DTO
        DoctorDto docdto = new DoctorDto();
        BeanUtils.copyProperties(entity, docdto);

        return docdto;
    }


    // ========================= EDIT DOCTOR =========================
    @Override
    public String editDoctor(DoctorDto docdto) {

        // First check if doctor exists
        DoctorEntity entity = doctorRepo.findById(docdto.getId())
                .orElseThrow(() -> 
                   new DoctorNotFoundException("Invalid Doctor Id: " + docdto.getId()));

        // Copy updated values from DTO to existing entity
        // Excluding audit fields (do not overwrite them)
        BeanUtils.copyProperties(docdto, entity, "updatedBy", "updatedDate");

        // Save updated entity
        doctorRepo.save(entity);

        return docdto.getId() + " Doctor Details are Updated";
    }


    // ========================= DELETE DOCTOR =========================
    @Override
    public String deleteDoctorById(int id) {

        // Check if record exists
        if (doctorRepo.existsById(id)) {

            // Delete by ID
            doctorRepo.deleteById(id);

            return id + " Doctor Found and Deleted";
        }

        return id + " Doctor Not Found and Not Deleted";
    }
}
