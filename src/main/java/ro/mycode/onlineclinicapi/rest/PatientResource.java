package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.PDFGenerator.PatientPDF;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.PatientDTO;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.service.PatientService;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping( value = "/api/v1/patient")
@Slf4j
public class PatientResource {

    private PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/getAllPatient")
    public ResponseEntity<List<Patient>> getAllPatient(){
    List<Patient> patientList = this.patientService.getAllPatient();

    log.info("REST request to get all patient {}",patientList);

    return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    @PostMapping("/addPatient")
    public ResponseEntity<CreateRestResponse> addPatient(@RequestBody PatientDTO patientDTO){
        log.info("REST request to add patient {}",patientDTO);

        this.patientService.addPatient(patientDTO);

        return new ResponseEntity<>(new CreateRestResponse("Patient was added!"),HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient/{patientName}")
    public ResponseEntity<CreateRestResponse> removePatient(@PathVariable String patientName){
        log.info("REST request to delete patient by name {}",patientName);

        this.patientService.deletePatient(patientName);

        return new ResponseEntity<>(new CreateRestResponse("Patient was deleted!"),HttpStatus.OK);
    }

    @GetMapping("/getPatientbyId/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id){

        log.info("REST request to get patient by id {}",id);

       Patient patient =  this.patientService.getPatientbyId(id);

        return new ResponseEntity<>(patient,HttpStatus.OK);

    }

    @GetMapping("/getPatientbyUsername/{username}")
    public ResponseEntity<Patient> getPatientByUsername(@PathVariable String username){
        log.info("REST request to get patient by username {}",username);

        Patient patient = this.patientService.getPatientByUsername(username);

        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @GetMapping("/getPatientByEmail/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email){
        log.info("REST request to get patient by email {}",email);

        Patient patient = this.patientService.getPatientByEmail(email);

        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @GetMapping("/getPatientByFullName")
    public ResponseEntity<Patient> getPatientByFullName(@RequestParam String fullName){
        log.info("REST request to get patient by full name {}",fullName);

        Patient patient = this.patientService.getPatientbyFullName(fullName);

        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<CreateRestResponse> updatePatient(@RequestBody PatientDTO patientDTO){
        log.info("REST request to update patient {} ",patientDTO);

        this.patientService.updatePatient(patientDTO);

        return new ResponseEntity<>(new CreateRestResponse("Patient was updated"),HttpStatus.OK);


    }

    @GetMapping("/exportPdf")
    public ResponseEntity<CreateRestResponse> exportPdf(HttpServletResponse response,@RequestParam String patientName) throws Exception {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=patientpdf_" + currentDate + ".pdf";

        Patient patient = this.patientService.getPatientbyFullName(patientName);

        PatientPDF patientPDF = new PatientPDF(patient);

        patientPDF.generate(response);

        return new ResponseEntity<>(new CreateRestResponse("PDF was created"),HttpStatus.OK);
    }


}
