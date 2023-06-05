package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.DoctorDTO;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.service.DoctorService;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@Slf4j
public class DoctorResource {

    public DoctorService doctorService;

    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/getAllDoctor")
    public ResponseEntity<List<Doctor>> getAllDoctor(){
        List<Doctor> doctorList = this.doctorService.getAllDoctor();

        log.info("REST request to get all doctor");

        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    @PostMapping("/addDoctor")
    public ResponseEntity<CreateRestResponse> addDoctor(@RequestBody DoctorDTO doctorDTO){
        this.doctorService.addDoctor(doctorDTO);

        log.info("REST request to add doctor {}",doctorDTO);

        return new ResponseEntity<>(new CreateRestResponse("Doctor was added"),HttpStatus.OK);
    }

    @DeleteMapping("/removeDoctor")
    public ResponseEntity<CreateRestResponse> removeDoctor(@RequestParam String doctorName){
        this.doctorService.removeDoctor(doctorName);

        log.info("REST request to remove doctor by name{}",doctorName);

        return new ResponseEntity<>(new CreateRestResponse("Doctor was removed!"),HttpStatus.OK);
    }

    @GetMapping("/getDoctorByName")
    public ResponseEntity<Doctor> getDoctorByName(@RequestParam String doctorName){
        Doctor doctor = this.doctorService.getDoctorByFullName(doctorName);

        log.info("REST request to get doctor by name{}",doctorName);

        return new ResponseEntity<>(doctor,HttpStatus.OK);
    }

    @GetMapping("/getDoctorByEmail/{email}")
    public ResponseEntity<Doctor> getDoctorbyEmail(@PathVariable String email){
        Doctor doctor = this.doctorService.getDoctorByEmail(email);

        log.info("REST request to remove doctor by email{}",email);

        return new ResponseEntity<>(doctor,HttpStatus.OK);
    }

    @GetMapping("/getDoctorByUsername/{username}")
    public ResponseEntity<Doctor> getDoctorByUsername(@PathVariable String username){

        Doctor doctor = this.doctorService.getDoctorByUsername(username);

        log.info("REST request to remove doctor by username{}",username);

        return new ResponseEntity<>(doctor,HttpStatus.OK);
    }
}
