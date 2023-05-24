package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
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

    public void addDoctor(Doctor doctor){
        Optional<Doctor> doctorOptional = this.doctorRepo.getDoctorByFullName(doctor.getFullName());

        if(doctorOptional.isEmpty()){
            doctorRepo.save(doctor);
        }else{
            throw new DoctorWasFoundException();
        }
    }

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
