package ro.mycode.onlineclinicapi.service;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.dto.DoctorDTO;
import ro.mycode.onlineclinicapi.exceptions.DoctorNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.DoctorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.repo.DoctorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    public DoctorRepo doctorRepo;

    public DoctorService(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public List<Doctor> getAllDoctor(){
        List<Doctor> doctors = this.doctorRepo.getAllDoctor();

        if(doctors.isEmpty()){
            throw new ListEmptyException();
        }

        return doctors;
    }

    @Modifying
    @Transactional
    public void addDoctor(DoctorDTO doctorDTO){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByFullName(doctorDTO.getFullName());

        if(doctorOptional.isEmpty()){
            Doctor doctor = Doctor.builder().adress(doctorDTO.getAdress())
                            .password(doctorDTO.getPassword())
                            .username(doctorDTO.getUsername())
                    .email(doctorDTO.getEmail())
                    .fullName(doctorDTO.getFullName())
                    .mobile(doctorDTO.getMobile())
                    .build();

            doctorRepo.save(doctor);
        }else{
            throw new DoctorWasFoundException();
        }
    }

    @Modifying
    @Transactional
    public void removeDoctor(String fullName){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByFullName(fullName);

        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException();
        }else{
            this.doctorRepo.delete(doctorOptional.get());
        }

    }

    @Modifying
    @Transactional
    public Doctor getDoctorByUsername(String username){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByUsername(username);

        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException();
        }

        return doctorOptional.get();
    }

    public Doctor getDoctorByEmail(String email){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByEmail(email);

        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException();
        }

        return doctorOptional.get();

    }

    public Doctor getDoctorByFullName(String fullName){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByFullName(fullName);
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException();
        }

        return doctorOptional.get();
    }
}
