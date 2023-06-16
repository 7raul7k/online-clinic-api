package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.PDFGenerator.TestPatientPDF;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.TestPatientDTO;
import ro.mycode.onlineclinicapi.models.TestPatient;
import ro.mycode.onlineclinicapi.service.TestPatientService;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/testPatient")
@Slf4j
public class TestPatientResource {

    public TestPatientService testPatientService;

    public TestPatientResource(TestPatientService testPatientService) {
        this.testPatientService = testPatientService;
    }

    @GetMapping("/getAllTestPatient")
    public ResponseEntity<List<TestPatient>> getAllTestPatient(){
        List<TestPatient> testPatientList = this.testPatientService.patientList();

        log.info("REST request to get all test patient {}",testPatientList);

        return new ResponseEntity<>(testPatientList, HttpStatus.OK);
    }

    @PostMapping(value = "/addTest",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateRestResponse> addTestPatient(@RequestBody TestPatientDTO testPatientDTO){
        log.info("REST request to add  test patient {}",testPatientDTO);

        this.testPatientService.addTestPatient(testPatientDTO);

        return new ResponseEntity<>(new CreateRestResponse("Test patient was added!"),HttpStatus.OK);
    }

    @DeleteMapping("/deleteTestPatient")
    public ResponseEntity<CreateRestResponse> deleteTestPatient(@RequestParam  String testName){
        log.info("REST request to delete test patient by name {}",testName);

        this.testPatientService.deleteTestPatient(testName);

        return new ResponseEntity<>(new CreateRestResponse("Test patient was deleted!"),HttpStatus.OK);
    }

    @GetMapping("/getTestPatientByName")
    public ResponseEntity<TestPatient> getTestPatientByName(@RequestParam String name){
        TestPatient testPatient = this.testPatientService.getTestPatientbyName(name);

        log.info("REST request to get test patient by name {}",name);

        return new ResponseEntity<>(testPatient,HttpStatus.OK);
    }

    @GetMapping("/getTestPatientByType")
    public ResponseEntity<List<TestPatient>> getTestByType(@RequestParam String type) {
        List<TestPatient> testPatientList = this.testPatientService.getTestbyType(type);

        log.info("REST request to get test patient by type {}", type);

        return new ResponseEntity<>(testPatientList,HttpStatus.OK);
    }

    @GetMapping("/getPatientByReport")
    public ResponseEntity<List<TestPatient>> getTestPatientByReport(@RequestParam String report){
        List<TestPatient> testPatientList = testPatientService.getTestByReport(report);

        log.info("REST request to get test patient by report{}", report);

        return new ResponseEntity<>(testPatientList,HttpStatus.OK);
    }

    @PutMapping("/updateTestPatient")
    public ResponseEntity<CreateRestResponse> updateTestPatient(@RequestBody TestPatientDTO testPatientDTO){

        this.testPatientService.updateTest(testPatientDTO);

        log.info("REST request to update test patient {}", testPatientDTO);

        return new ResponseEntity<>(new CreateRestResponse("Test patient was updated "),HttpStatus.OK);
    }

    @GetMapping("/exportPDF")
    public ResponseEntity<CreateRestResponse> exportPdf(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=testpatientpdf_" + currentDate +".pdf";

        List<TestPatient> testPatientList = this.testPatientService.patientList();

        TestPatientPDF testPatientPDF = new TestPatientPDF(testPatientList);

        testPatientPDF.generate(response);

        return new ResponseEntity<>(new CreateRestResponse("PDF was created"),HttpStatus.OK);


    }
}
