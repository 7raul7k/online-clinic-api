package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.exceptions.AdministratorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.AppointmentNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Administrator;
import ro.mycode.onlineclinicapi.repo.AdministratorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    public AdministratorRepo adminRepo;

    public AdministratorService(AdministratorRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public List<Administrator> getAllAdministrators(){

        List<Administrator> administratorList = this.adminRepo.getAllAdmin();

        if(administratorList.isEmpty()){
            throw new ListEmptyException();
        }

        return administratorList;
    }

    public void addAdministrator(Administrator administrator){

        Optional<Administrator> optionalAdministrator = this.adminRepo.getAdminByFullName(administrator.getFullName());

        if(optionalAdministrator.isEmpty()){

            this.adminRepo.save(administrator);
        }else{
        throw new AdministratorWasFoundException();
        }

    }

    public void removeAdministrator(String fullName){

        Optional<Administrator> administrator = this.adminRepo.getAdminByFullName(fullName);

        if(administrator.isEmpty()){
            throw new AppointmentNotFoundException();
        }else{
            this.adminRepo.delete(administrator.get());
        }
    }

    public Administrator getAdministratorByFullName(String fullName){
        Optional<Administrator> administrator = this.adminRepo.getAdminByFullName(fullName);

        if(administrator.isEmpty()){
            throw new AppointmentNotFoundException();
        }
        return administrator.get();
    }

    public Administrator getAdministratorById(int id){

        Optional<Administrator> administrator = this.adminRepo.getAdminById((long) id);

        if(administrator.isEmpty()){
            throw new AppointmentNotFoundException();
        }
        return administrator.get();

    }

    public Administrator getAdministrator(String username){

        Optional<Administrator> administrator = this.adminRepo.getAdminByUsername(username);

        if(administrator.isEmpty()){
            throw new AppointmentNotFoundException();
        }
        return administrator.get();

    }
}
