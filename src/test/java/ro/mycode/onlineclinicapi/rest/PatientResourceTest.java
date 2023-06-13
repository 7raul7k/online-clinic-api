package ro.mycode.onlineclinicapi.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ro.mycode.onlineclinicapi.dto.PatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PatientNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PatientWasFoundException;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.service.PatientService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PatientResourceTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientResource patientResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){ restMockMvc = MockMvcBuilders.standaloneSetup(patientResource).build();}

    @Test
    public void getAllPatient() throws Exception{

        Faker faker = new Faker();

        List<Patient> patientList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++ ){

            patientList.add(Patient.builder().id((long) i)
                    .number(faker.lorem().characters())
                    .email(faker.lorem().characters())
                    .password(faker.lorem().characters())
                    .fullName(faker.name().fullName())
                    .adress(faker.address().fullAddress())
                    .build());
        }

        doReturn(patientList).when(patientService).getAllPatient();

        restMockMvc.perform(get("/api/v1/patient/getAllPatient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(mapper.writeValueAsString(patientList)));
    }

    @Test
    public void getAllPatientBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(patientService).getAllPatient();

        restMockMvc.perform(get("/api/v1/patient/getAllPatient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    public void addPatient() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doNothing().when(patientService).addPatient(patientDTO);

        restMockMvc.perform(post("/api/v1/patient/addPatient").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(patientDTO))).andExpect(status().isOk());
    }

    @Test
    public void addPatientBadRequest() throws Exception{
        doThrow(PatientWasFoundException.class).when(patientService).addPatient(new PatientDTO());

        restMockMvc.perform(post("/api/v1/patient/addPatient").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new PatientDTO()))).andExpect(status().isBadRequest());

    }

    @Test
    public void removePatient() throws Exception{
        doNothing().when(patientService).deletePatient("patient");

        restMockMvc.perform(delete("/api/v1/patient/deletePatient/patient")).andExpect(status().isOk());
    }

    @Test
    public void removePatientBadRequest() throws Exception{
        doThrow(PatientNotFoundException.class).when(patientService).deletePatient("patient");

        restMockMvc.perform(delete("/api/v1/patient/deletePatient/patient")).andExpect(status().isBadRequest());
    }

    @Test
    public void getPatientByUsername() throws Exception{
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();


        doReturn(patient).when(patientService).getPatientByUsername("cristicristian");

        restMockMvc.perform(get("/api/v1/patient/getPatientbyUsername/cristicristian")).andExpect(status().isOk());
    }

    @Test
    public void getPatientByUsernameBadRequest() throws Exception{

        doThrow(PatientNotFoundException.class).when(patientService).getPatientByUsername("patient");

        restMockMvc.perform(get("/api/v1/patient/getPatientbyUsername/patient")).andExpect(status().isBadRequest());


    }

    @Test
    public void getPatientById() throws Exception{
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(patient).when(patientService).getPatientbyId(1);

        restMockMvc.perform(get("/api/v1/patient/getPatientbyId/1")).andExpect(status().isOk());


    }

    @Test
    public void getPatientByIdBadRequest() throws Exception{

        doThrow(PatientNotFoundException.class).when(patientService).getPatientbyId(1);

        restMockMvc.perform(get("/api/v1/patient/getPatientbyId/1")).andExpect(status().isBadRequest());

    }

    @Test
    public void getPatientByEmail() throws Exception{
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(patient).when(patientService).getPatientByEmail(patient.getEmail());

        restMockMvc.perform(get("/api/v1/patient/getPatientByEmail/cristidinescu@gmail.com")).andExpect(status().isOk());



    }

    @Test
    public  void getPatientByEmailBadRequest() throws Exception{
        doThrow(PatientNotFoundException.class).when(patientService).getPatientByEmail("test");

        restMockMvc.perform(get("/api/v1/patient/getPatientByEmail/test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getPatientByFullName() throws Exception{
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(patient).when(patientService).getPatientbyFullName("Cristi Dinescu");

        restMockMvc.perform(get("/api/v1/patient/getPatientByFullName?fullName=Cristi Dinescu")).andExpect(status().isOk());

    }

    @Test
    public void getPatientByFullNameBadRequest() throws Exception{

        doThrow(PatientNotFoundException.class).when(patientService).getPatientbyFullName("test");

        restMockMvc.perform(get("/api/v1/patient/getPatientByFullName?fullName=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void updatePatient()throws Exception{
        PatientDTO patientDTO = PatientDTO.builder()
                .fullName("Cristi Dinescu")
                .adress("Sibiu").number("0758322325")
                .email("cristidinescu2023@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doNothing().when(patientService).updatePatient(patientDTO);

        restMockMvc.perform(put("/api/v1/patient/updatePatient").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(patientDTO))).andExpect(status().isOk());
    }

    @Test
    public void updatePatientBadRequest() throws Exception{
        doThrow(PatientNotFoundException.class).when(patientService).updatePatient(new PatientDTO());

        restMockMvc.perform(put("/api/v1/patient/updatePatient").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new PatientDTO()))).andExpect(status().isBadRequest());

    }



}