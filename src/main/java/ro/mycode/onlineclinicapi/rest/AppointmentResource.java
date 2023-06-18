package ro.mycode.onlineclinicapi.rest;


import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.PDFGenerator.AppointmentPDF;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.CreateVisitRequest;
import ro.mycode.onlineclinicapi.models.Appointment;
import ro.mycode.onlineclinicapi.service.AppointmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@Slf4j
public class AppointmentResource {

    public AppointmentService appointmentService;

    public AppointmentResource(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllAppointment")
    public ResponseEntity<List<Appointment>> getAllAppointment(){

        List<Appointment> appointments = this.appointmentService.getAllAppointment();
        log.info("REST request to get all appointment {}",appointments);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @DeleteMapping("/removeAppointment/{number}")
    public ResponseEntity<CreateRestResponse> removeAppointment(@PathVariable String number){

        this.appointmentService.deleteAppointment(number);

        log.info("REST request to remove appointment by number {}",number);

        return new ResponseEntity<>(new CreateRestResponse("Appointment was removed!"),HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByNumber/{number}")
    public ResponseEntity<Appointment> getAppointmentbyNumber(@PathVariable String number){
        Appointment appointment = this.appointmentService.getAppointmentByNumber(number);

        log.info("REST request to get all appointment by number {}",number);

        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByType/{type}")
    public ResponseEntity<List<Appointment>> getAppointmentByType(@PathVariable String type){
        List<Appointment> appointmentList = this.appointmentService.getAppointmentbyType(type);

        log.info("REST request to get all appointment by type  {}",type);

        return new ResponseEntity<>(appointmentList,HttpStatus.OK);
    }

    @GetMapping("/getAppointmentDate")
    public ResponseEntity<List<Appointment>> getAppointmentByDate(@RequestParam LocalDate date){

        List<Appointment> appointmentList = this.appointmentService.getAppointmentbyDate(date);

        log.info("REST request to get all appointment by date  {}",date);

        return new ResponseEntity<>(appointmentList,HttpStatus.OK);
    }

    @PostMapping("/createVisit")
    public ResponseEntity<CreateRestResponse> createVisit(@RequestBody CreateVisitRequest createVisitRequest){
        this.appointmentService.createVisit(createVisitRequest);

        log.info("REST request to create appointment {}",createVisitRequest);

        return new ResponseEntity<>(new CreateRestResponse("Appointment was created!"),HttpStatus.OK);
    }

    @GetMapping("/exportPDF")
    public ResponseEntity<CreateRestResponse> exportPDF(HttpServletResponse response,@RequestParam LocalDate date) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDate = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=appointmentpdf_" + currentDate +".pdf";

        response.setHeader(headerKey,headerValue);

        List<Appointment> appointments = this.appointmentService.getAppointmentbyDate(date);

        AppointmentPDF appointmentPDF = new AppointmentPDF(appointments);

        appointmentPDF.generate(response);

        return new ResponseEntity<>(new CreateRestResponse("PDF was created!"),HttpStatus.OK);

    }

}
