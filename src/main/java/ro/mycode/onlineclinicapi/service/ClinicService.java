package ro.mycode.onlineclinicapi.service;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.exceptions.ClinicNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.ClinicWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Clinic;
import ro.mycode.onlineclinicapi.repo.ClinicRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    public ClinicRepo clinicRepo;

    public ClinicService(ClinicRepo clinicRepo) {
        this.clinicRepo = clinicRepo;
    }

    public List<Clinic> getAllClinic(){
        List<Clinic> clinicList = this.clinicRepo.getAllClinic();

        if(clinicList.isEmpty()){
            throw new ListEmptyException();
        }

        return clinicList;
    }

    @Modifying
    @Transactional
    public void addClinic(Clinic clinic){
        Optional<Clinic> clinicOptional = this.clinicRepo.getClinicByName(clinic.getName());

        if(clinicOptional.isEmpty()){

            clinicRepo.save(clinic);

        }else{
            throw new ClinicWasFoundException();
        }
    }

    @Modifying
    @Transactional
    public void removeClinic(String name){
        Optional<Clinic> clinicOptional = this.clinicRepo.getClinicByName(name);

        if(clinicOptional.isEmpty()){
            throw new ClinicNotFoundException();
        }else{
            this.clinicRepo.delete(clinicOptional.get());
        }
    }

    public Clinic getClinicByName(String clinicName){
        Optional<Clinic> clinic = this.clinicRepo.getClinicByName(clinicName);

        if(clinic.isEmpty()){
            throw new ClinicNotFoundException();
        }

        return clinic.get();
    }

    public List<Clinic> getClinicByPlace(String place){
        List<Clinic> clinic = this.clinicRepo.getClinicByPlace(place);

        if(clinic.isEmpty()){
            throw new ClinicNotFoundException();
        }

        return clinic;
    }

    public  List<Clinic> getClinicByType(String type){
        List<Clinic> clinic = this.clinicRepo.getClinicByType(type);

        if(clinic.isEmpty()){
            throw new ClinicNotFoundException();
        }

        return clinic;

    }

}
