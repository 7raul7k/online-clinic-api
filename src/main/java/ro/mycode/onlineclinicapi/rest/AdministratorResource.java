package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.models.Administrator;
import ro.mycode.onlineclinicapi.service.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrator")
@Slf4j
public class AdministratorResource {

    public AdministratorService administratorService;

    public AdministratorResource(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping("/getAllAdministrators")
    public ResponseEntity<List<Administrator>> getAllAdministrators(){
        List<Administrator> administratorList = this.administratorService.getAllAdministrators();

        log.info("REST request to get all administrators {}",administratorList);

        return new ResponseEntity<>(administratorList, HttpStatus.OK);
    }

    @PostMapping("/addAdministrator")
    public ResponseEntity<CreateRestResponse> addAdministrator(@RequestBody Administrator administrator){

        this.administratorService.addAdministrator(administrator);

        log.info("REST request to add administrator {}",administrator);

        return new ResponseEntity<>(new CreateRestResponse("Administrator was added!"),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdministrator")
    public ResponseEntity<CreateRestResponse> removeAdministrator(@RequestParam String fullName){

        this.administratorService.removeAdministrator(fullName);

        log.info("REST request to delete administrator by full name {}",fullName);

        return new ResponseEntity<>(new CreateRestResponse("Administrator was deleted!"),HttpStatus.OK);
    }

    @GetMapping("/getAdministratorByFullName")
    public ResponseEntity<Administrator> getAdministratorByFullName(@RequestParam String fullName){

        Administrator administrator = this.administratorService.getAdministratorByFullName(fullName);

        log.info("REST request to get administrator by full name {}",fullName);

        return new ResponseEntity<>(administrator,HttpStatus.OK);
    }

    @GetMapping("/getAdministratorById/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable int id){

        Administrator administrator = this.administratorService.getAdministratorById(id);

        log.info("REST request to get administrator by id {}",id);

        return new ResponseEntity<>(administrator,HttpStatus.OK);
    }

    @GetMapping("/getAdministratorByUsername")
    public ResponseEntity<Administrator> getAdministratorByUsername(@RequestParam String username){

        Administrator administrator = this.administratorService.getAdministratorByUsername(username);

        log.info("REST request to get administrator by username {}",username);

        return new ResponseEntity<>(administrator,HttpStatus.OK);
    }




}
