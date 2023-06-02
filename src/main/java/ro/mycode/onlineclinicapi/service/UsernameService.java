package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.dto.UsernameDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.UsernameNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.UsernameWasFoundException;
import ro.mycode.onlineclinicapi.models.Username;
import ro.mycode.onlineclinicapi.repo.UsernameRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UsernameService {

    public UsernameRepo usernameRepo;

    public UsernameService(UsernameRepo usernameRepo) {
        this.usernameRepo = usernameRepo;
    }


    public List<Username> getAllUsername(){
        List<Username> usernameList = this.usernameRepo.getAllUsernames();

        if(usernameList.isEmpty()){
            throw new ListEmptyException();
        }

        return usernameList;
    }

    public void addUsername(UsernameDTO usernameDTO){
        Optional<Username> usernameOptional = this.usernameRepo.getUsernameByName(usernameDTO.getName());

        if(usernameOptional.isEmpty()){
            Username username = Username.builder().name(usernameDTO.getName())
                    .dob(usernameDTO.getDob())
                    .address(usernameDTO.getAddress())
                    .email(usernameDTO.getEmail())
                    .build();
            this.usernameRepo.save(username);
        }else{
            throw new UsernameWasFoundException();
        }
    }

    public void deleteUsername(String name){
        Optional<Username> usernameOptional = this.usernameRepo.getUsernameByName(name);

        if(usernameOptional.isEmpty()){
            throw new UsernameNotFoundException();
        }else {
            this.usernameRepo.delete(usernameOptional.get());
        }
    }

    public Username getUsernameByName(String name){
        Optional<Username> usernameOptional = this.usernameRepo.getUsernameByName(name);

        if(usernameOptional.isEmpty()){
            throw new UsernameNotFoundException();
        }

        return usernameOptional.get();
    }

    public Username getUsernameByEmail(String email){
        Optional<Username> usernameOptional = this.usernameRepo.getUsernameByEmail(email);

        if(usernameOptional.isEmpty()){
            throw new UsernameNotFoundException();
        }

        return usernameOptional.get();
    }

    public List<Username> getAllUsernameByAdress(String adress){
        List<Username> usernameList = this.usernameRepo.getUsernameByAddress(adress);

        if(usernameList.isEmpty()){
            throw new ListEmptyException();
        }

        return usernameList;
    }

    public void updateUsername(UsernameDTO usernameDTO){

        Optional<Username> usernameOptional = this.usernameRepo.getUsernameByName(usernameDTO.getName());

        if(usernameOptional.isEmpty()){
            throw new UsernameNotFoundException();
        }

        if(usernameDTO.getAddress() != null){

            usernameOptional.get().setAddress(usernameDTO.getAddress());
        }else if (usernameDTO.getEmail() != null){
            usernameOptional.get().setEmail(usernameDTO.getEmail());
        }else if(usernameDTO.getDob() != null){
            usernameOptional.get().setDob(usernameDTO.getDob());
        }

        this.usernameRepo.saveAndFlush(usernameOptional.get());
    }
}
