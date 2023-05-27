package ro.mycode.onlineclinicapi.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void addPatient(Patient patient){
        Optional<Patient> patient1 = this.patientRepo.getPatientByFullName(patient.getFullName());

        if(patient1.isEmpty()){
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
}
