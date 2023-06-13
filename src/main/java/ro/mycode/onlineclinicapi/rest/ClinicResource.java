package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.PDFGenerator.ClinicPDF;
import ro.mycode.onlineclinicapi.dto.ClinicDTO;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.models.Clinic;
import ro.mycode.onlineclinicapi.service.ClinicService;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic")
@Slf4j
public class ClinicResource {

    public ClinicService clinicService;

    public ClinicResource(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/getAllClinic")
    public ResponseEntity<List<Clinic>> getAllClinic(){
     List<Clinic> clinicList = this.clinicService.getAllClinic();

     log.info("REST request to get all clinic");

     return new ResponseEntity<>(clinicList, HttpStatus.OK);
    }

    @PostMapping("/addClinic")
    public ResponseEntity<CreateRestResponse> addClinic(@RequestBody ClinicDTO clinicDTO){
         this.clinicService.addClinic(clinicDTO);

        log.info("REST request to add clinic  {}",clinicDTO);

        return new ResponseEntity<>(new CreateRestResponse("Clinic was added!"),HttpStatus.OK);
    }


    @DeleteMapping("/removeClinic/{clinicName}")
    public ResponseEntity<CreateRestResponse> removeClinic(@PathVariable String clinicName){

        this.clinicService.removeClinic(clinicName);

        log.info("REST request to remove clinic by name  {}",clinicName);

        return new ResponseEntity<>(new CreateRestResponse("Clinic was deleted!"),HttpStatus.OK);
    }

    @GetMapping("/getClinicByName")
    public ResponseEntity<Clinic> getClinicByName(@RequestParam String name){
        Clinic clinic = this.clinicService.getClinicByName(name);

        log.info("REST request to get clinic by name {} ",name);

        return new ResponseEntity<>(clinic,HttpStatus.OK);
    }

    @GetMapping("/getClinicByPlace")
    public ResponseEntity<List<Clinic>> getClinicByPlace(@RequestParam String place){

        List<Clinic> clinicList = this.clinicService.getClinicByPlace(place);

        log.info("REST request to get clinic by place {} ",place);

        return new ResponseEntity<>(clinicList,HttpStatus.OK);
    }

    @GetMapping("/getClinicByType/{type}")
    public ResponseEntity<List<Clinic>> getClinicByType(@PathVariable String type){
       List<Clinic> clinicList = this.clinicService.getClinicByType(type);

        log.info("REST request to get clinic by type {} ",type);

        return new ResponseEntity<>(clinicList,HttpStatus.OK);
    }

    @PutMapping("/updateClinic")
    public ResponseEntity<CreateRestResponse> updateClinic(@RequestBody ClinicDTO clinicDTO){
        this.clinicService.updateClinic(clinicDTO);

        log.info("REST request to update clinic  {}",clinicDTO);

        return new ResponseEntity<>(new CreateRestResponse("Clinic was updated!"),HttpStatus.OK);

    }

    @GetMapping("/exportPDF")
    public ResponseEntity<CreateRestResponse> exportPDF(HttpServletResponse response,@RequestParam String clinicName) throws Exception{

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=clinicpdf_" + currentDate + ".pdf";

        Clinic clinic = this.clinicService.getClinicByName(clinicName);

        ClinicPDF clinicPDF = new ClinicPDF(clinic);

        clinicPDF.generate(response);

        return new ResponseEntity<>(new CreateRestResponse("PDF was created"),HttpStatus.OK);
    }


}
