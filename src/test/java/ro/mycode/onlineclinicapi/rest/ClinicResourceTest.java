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
import ro.mycode.onlineclinicapi.dto.ClinicDTO;
import ro.mycode.onlineclinicapi.exceptions.ClinicNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.ClinicWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Clinic;
import ro.mycode.onlineclinicapi.service.ClinicService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ClinicResourceTest {

    @Mock
    private ClinicService clinicService;

    @InjectMocks
    private ClinicResource clinicResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){restMockMvc = MockMvcBuilders.standaloneSetup(clinicResource).build();}

    @Test
    public void getAllClinic() throws Exception{
        Faker faker = new Faker();

        List<Clinic> clinicList = new ArrayList<>();
        for(int i = 0 ; i < 10 ;i++){
            clinicList.add(Clinic.builder().name(faker.lorem().sentence()).type(faker.lorem().sentence()).description(faker.lorem().sentence()).place(faker.lorem().sentence()).address(faker.lorem().sentence()).build());

        }

        doReturn(clinicList).when(clinicService).getAllClinic();

        restMockMvc.perform(get("/api/v1/clinic/getAllClinic")).andExpect(status().isOk());

    }

    @Test
    public void getAllClinicBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(clinicService).getAllClinic();

        restMockMvc.perform(get("/api/v1/clinic/getAllClinic")).andExpect(status().isBadRequest());

    }

    @Test
    public void addClinic() throws Exception{
        ClinicDTO clinicDTO = ClinicDTO.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doNothing().when(clinicService).addClinic(clinicDTO);

        restMockMvc.perform(post("/api/v1/clinic/addClinic").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(clinicDTO))).andExpect(status().isOk());

    }

    @Test
    public void addClinicBadRequest() throws Exception{

        doThrow(ClinicWasFoundException.class).when(clinicService).addClinic(new ClinicDTO());

        restMockMvc.perform(post("/api/v1/clinic/addClinic").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new ClinicDTO()))).andExpect(status().isBadRequest());

    }

    @Test
    public void removeClinic() throws Exception{

        doNothing().when(clinicService).removeClinic("clinic");

        restMockMvc.perform(delete("/api/v1/clinic/removeClinic/clinic")).andExpect(status().isOk());

    }

    @Test
    public void removeClinicBadRequest() throws Exception{

        doThrow(ClinicNotFoundException.class).when(clinicService).removeClinic("test");

        restMockMvc.perform(delete("/api/v1/clinic/removeClinic/test")).andExpect(status().isBadRequest());


    }

    @Test
    public void getClinicByName() throws Exception{
        Clinic clinic = Clinic.builder().name("clinic").address("Bucuresti").place("test").type("clinic").description("test").build();


        doReturn(clinic).when(clinicService).getClinicByName("clinic");

        restMockMvc.perform(get("/api/v1/clinic/getClinicByName?name=clinic")).andExpect(status().isOk());

    }

    @Test
    public void getClinicByNameBadRequest() throws Exception{

        doThrow(ClinicNotFoundException.class).when(clinicService).getClinicByName("test");

        restMockMvc.perform(get("/api/v1/clinic/getClinicByName?name=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getClinicByPlace() throws Exception{
        Faker faker = new Faker();

        List<Clinic> clinicList =  new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){

            clinicList.add(Clinic.builder().place(faker.lorem().sentence()).description(faker.lorem().sentence()).type(faker.lorem().sentence()).address(faker.lorem().sentence()).name(faker.lorem().sentence()).build());
        }

        doReturn(clinicList).when(clinicService).getClinicByPlace("bucuresti");

        restMockMvc.perform(get("/api/v1/clinic/getClinicByPlace?place=bucuresti")).andExpect(status().isOk());

    }

    @Test
    public void getClinicByTypeBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(clinicService).getClinicByType("test1");

        restMockMvc.perform(get("/api/v1/clinic/getClinicByPlace?place=test1")).andExpect(status().isBadRequest());

    }

    @Test
    public void updateClinic() throws Exception{

        ClinicDTO clinicDTO = ClinicDTO.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doNothing().when(clinicService).updateClinic(clinicDTO);

        restMockMvc.perform(put("/api/v1/clinic/updateClinic").content(mapper.writeValueAsString(clinicDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void updateClinicBadRequest() throws Exception{

        doThrow(ClinicNotFoundException.class).when(clinicService).updateClinic(new ClinicDTO());

        restMockMvc.perform(put("/api/v1/clinic/updateClinic").content(mapper.writeValueAsString(new ClinicDTO())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());


    }

}