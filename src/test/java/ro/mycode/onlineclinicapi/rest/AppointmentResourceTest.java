package ro.mycode.onlineclinicapi.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.App;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.mycode.onlineclinicapi.dto.AppointmentDTO;
import ro.mycode.onlineclinicapi.dto.CreateVisitRequest;
import ro.mycode.onlineclinicapi.exceptions.AppointmentNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.AppointmentWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PatientNotFoundException;
import ro.mycode.onlineclinicapi.models.Appointment;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.service.AppointmentService;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class AppointmentResourceTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentResource appointmentResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig() { restMockMvc = MockMvcBuilders.standaloneSetup(appointmentResource).build();}

    @Test
    public void getAllAppointment() throws Exception{
        Faker faker = new Faker();

        List<Appointment> appointmentList = new ArrayList<>();
        for(int i = 0 ; i < 10;i++){

            appointmentList.add(Appointment.builder().id((long) i).number(faker.number().digit()).type(faker.lorem().sentence()).description(faker.lorem().sentence()).build());

        }

        doReturn(appointmentList).when(appointmentService).getAllAppointment();

        restMockMvc.perform(get("/api/v1/appointment/getAllAppointment")).andExpect(status().isOk());
    }

    @Test
    public void getAllAppointmentBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(appointmentService).getAllAppointment();

        restMockMvc.perform(get("/api/v1/appointment/getAllAppointment")).andExpect(status().isBadRequest());

    }


    @Test
    public void removeAppointment() throws Exception{

        doNothing().when(appointmentService).deleteAppointment("number");

        restMockMvc.perform(delete("/api/v1/appointment/removeAppointment/number")).andExpect(status().isOk());


    }

    @Test
    public void removeAppointmentBadRequest() throws Exception{
        doThrow(AppointmentWasFoundException.class).when(appointmentService).deleteAppointment("test");

        restMockMvc.perform(delete("/api/v1/appointment/removeAppointment/test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getAppointmentByNumber() throws Exception{
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.now()).number("07326482134").type("clinic").build();

        doReturn(appointment).when(appointmentService).getAppointmentByNumber(appointment.getNumber());

        restMockMvc.perform(get("/api/v1/appointment/getAppointmentByNumber/07326482134")).andExpect(status().isOk());
    }

    @Test
    public void getAppointmentByNumberBadRequest() throws Exception{

        doThrow(AppointmentNotFoundException.class).when(appointmentService).getAppointmentByNumber("test");

        restMockMvc.perform(get("/api/v1/appointment/getAppointmentByNumber/test")).andExpect(status().isBadRequest());
    }

    @Test
    public void getAppointmentByType() throws Exception{

       Faker faker = new Faker();

       List<Appointment> appointmentList = new ArrayList<>();

       for(int i = 0 ; i < 10 ; i++){
           appointmentList.add(Appointment.builder().description(faker.lorem().sentence()).number(faker.number().digit()).type("clinic").build());

       }

       doReturn(appointmentList).when(appointmentService).getAppointmentbyType("clinic");
        restMockMvc.perform(get("/api/v1/appointment/getAppointmentByType/clinic")).andExpect(status().isOk());

    }

    @Test
    public void getAppointmentByTypeBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(appointmentService).getAppointmentbyType("test");

        restMockMvc.perform(get("/api/v1/appointment/getAppointmentByType/test")).andExpect(status().isBadRequest());


    }

    @Test
    public void createVisit()throws Exception{

        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();

        Patient patient = Patient.builder().fullName("Cristi Dinescu").adress("Baia Mare").number("0758932325").email("cristidinescu@gmail.com").password("cristidinescu@gmail.com2023").username("cristicristian").build();


        CreateVisitRequest visitRequest  = CreateVisitRequest.builder().description("test").doctorName(doctor.getFullName()).patientNumber(patient.getNumber()).patientEmail(patient.getEmail()).build();

         restMockMvc.perform(put("/api/v1/appointment/createVisit").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(visitRequest)));
    }

    @Test
    public void createVisitBadRequest() throws Exception{
        doThrow(PatientNotFoundException.class).when(appointmentService).createVisit(new CreateVisitRequest());

        restMockMvc.perform(put("/api/v1/appointment/createVisit").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new CreateVisitRequest()))).andExpect(status().isBadRequest() );

    }



}