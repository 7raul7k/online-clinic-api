package ro.mycode.onlineclinicapi.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.dto.PatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PatientNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PatientWasFoundException;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.repo.PatientRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepo patientRepo;

    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public List<Patient> getAllPatient(){
        List<Patient> patientList = this.patientRepo.getAllPatient();

        if(patientList.isEmpty()){
            throw new ListEmptyException();
        }

        return patientList;
    }


    @Modifying
    @Transactional
    public void addPatient(PatientDTO patientDTO){
        Optional<Patient> patient1 = this.patientRepo.getPatientByFullName(patientDTO.getFullName());

        if(patient1.isEmpty()){
           Patient patient = Patient.builder().fullName(patientDTO.getFullName())
                   .number(patientDTO.getNumber())
                   .password(patientDTO.getPassword())
                   .adress(patientDTO.getAdress())
                   .username(patientDTO.getUsername())
                   .email(patientDTO.getEmail())
                   .build();

           patientRepo.save(patient);
        }else{
            throw new PatientWasFoundException();
        }

    }

    @Modifying
    @Transactional
    public void deletePatient(String patientFullName){
        Optional<Patient> patient = this.patientRepo.getPatientByFullName(patientFullName);

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }else{
            this.patientRepo.delete(patient.get());
        }

    }

    public Patient getPatientbyId(int id){
        Optional<Patient> patient = this.patientRepo.getPatientById((long) id);

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }
        return patient.get();
    }

    public Patient getPatientByUsername(String username){
        Optional<Patient> patient = this.patientRepo.getPatientByUsername(username);

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }

        return patient.get();
    }

    public Patient getPatientByEmail(String email){
        Optional<Patient> patient = this.patientRepo.getPatientByEmail(email);

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }

        return patient.get();

    }

    public Patient getPatientbyFullName(String fullName){
        Optional<Patient> patient = this.patientRepo.getPatientByFullName(fullName);

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }

        return patient.get();
    }

    public void updatePatient(PatientDTO patientDTO){
        Optional<Patient> patient = this.patientRepo.getPatientByFullName(patientDTO.getFullName());

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }

        if(patientDTO.getAdress() != null){
            patient.get().setAdress(patientDTO.getAdress());
        }else if(patientDTO.getUsername() != null){
            patient.get().setUsername(patientDTO.getUsername());
        } else if (patientDTO.getNumber() != null) {
            patient.get().setNumber(patientDTO.getNumber());
        } else if(patientDTO.getEmail() != null){
            patient.get().setEmail(patientDTO.getEmail());
        }else if(patientDTO.getPassword() != null){
            patient.get().setPassword(patientDTO.getPassword());
        }

        patientRepo.saveAndFlush(patient.get());
    }
}
