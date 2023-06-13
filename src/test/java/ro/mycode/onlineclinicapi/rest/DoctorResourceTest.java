package ro.mycode.onlineclinicapi.rest;

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
import ro.mycode.onlineclinicapi.dto.DoctorDTO;
import ro.mycode.onlineclinicapi.exceptions.DoctorNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.DoctorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PatientNotFoundException;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.service.DoctorService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class DoctorResourceTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorResource doctorResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){restMockMvc = MockMvcBuilders.standaloneSetup(doctorResource).build();}

    @Test
    public void getAllDoctor() throws Exception{

        Faker faker = new Faker();

        List<Doctor> doctorList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++ ){
            doctorList.add(Doctor.builder().username(faker.lorem().sentence())
                    .email(faker.lorem().characters())
                    .fullName(faker.name().fullName())
                    .mobile(faker.number().digit())
                    .adress(faker.address().fullAddress())
                    .password(faker.lorem().characters())
                    .id((long) i).build());
        }

        doReturn(doctorList).when(doctorService).getAllDoctor();

        restMockMvc.perform(get("/api/v1/doctor/getAllDoctor")).andExpect(status().isOk());
    }

    @Test
    public void getAllDoctorBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(doctorService).getAllDoctor();

        restMockMvc.perform(get("/api/v1/doctor/getAllDoctor")).andExpect(status().isBadRequest());

    }

    @Test
    public void addDoctor() throws Exception{
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doNothing().when(doctorService).addDoctor(doctorDTO);

        restMockMvc.perform(post("/api/v1/doctor/addDoctor").content(mapper.writeValueAsString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    public void addDoctorBadRequest() throws Exception{
        doThrow(DoctorWasFoundException.class).when(doctorService).addDoctor(new DoctorDTO());

        restMockMvc.perform(post("/api/v1/doctor/addDoctor").content(mapper.writeValueAsString(new DoctorDTO())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    public void removeDoctor() throws Exception{

        doNothing().when(doctorService).removeDoctor("popescu");

        restMockMvc.perform(delete("/api/v1/doctor/removeDoctor?doctorName=popescu")).andExpect(status().isOk());

    }

    @Test
    public void removeDoctorBadRequest() throws Exception{

        doThrow(PatientNotFoundException.class).when(doctorService).removeDoctor("test");

        restMockMvc.perform(delete("/api/v1/doctor/removeDoctor?doctorName=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getDoctorByName() throws Exception{
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(doctor).when(doctorService).getDoctorByFullName(doctor.getFullName());

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByName?doctorName=David Moisescu")).andExpect(status().isOk());


    }

    @Test
    public void getDoctorByNameBadRequest() throws Exception{

        doThrow(DoctorNotFoundException.class).when(doctorService).getDoctorByFullName("test");

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByName?doctorName=test")).andExpect(status().isBadRequest());
    }

    @Test
    public void getDoctorByEmail() throws Exception{
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();
        doReturn(doctor).when(doctorService).getDoctorByEmail(doctor.getEmail());

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByEmail/davidmoisescu@gmail.com")).andExpect(status().isOk());
    }

    @Test
    public void getDoctorByEmailBadRequest() throws Exception{

        doThrow(DoctorNotFoundException.class).when(doctorService).getDoctorByEmail("test");

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByEmail/test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getDoctorByUsername() throws Exception{
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(doctor).when(doctorService).getDoctorByUsername(doctor.getUsername());

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByUsername/davidmoise")).andExpect(status().isOk());

    }

    @Test
    public void getDoctorByUsernameBadRequest() throws Exception{

        doThrow(DoctorNotFoundException.class).when(doctorService).getDoctorByUsername("test");

        restMockMvc.perform(get("/api/v1/doctor/getDoctorByUsername/test")).andExpect(status().isBadRequest());

    }
}