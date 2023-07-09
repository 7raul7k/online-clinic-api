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
import ro.mycode.onlineclinicapi.exceptions.AdministratorNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.AdministratorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Administrator;
import ro.mycode.onlineclinicapi.service.AdministratorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AdministratorResourceTest {

    @Mock
    public AdministratorService administratorService;

    @InjectMocks
    public AdministratorResource administratorResource;


    public ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){restMockMvc = MockMvcBuilders.standaloneSetup(administratorResource).build();
    }

    @Test
    public void getAllAdministrators() throws Exception {

        Faker faker  = new Faker();

        List<Administrator> administratorList = new ArrayList<>();

        for(int i = 0 ;i < 10 ; i++){

            administratorList.add(Administrator.builder().id((long) i).fullName(faker.name().fullName()).email(faker.lorem().sentence()).username(faker.lorem().sentence()).build());
        }

        doReturn(administratorList).when(administratorService).getAllAdministrators();

        restMockMvc.perform(get("/api/v1/administrator/getAllAdministrators")).andExpect(status().isOk());

    }

    @Test
    public void getAllAdministratorsBadRequest() throws Exception{

    doThrow(ListEmptyException.class).when(administratorService).getAllAdministrators();

    restMockMvc.perform(get("/api/v1/administrator/getAllAdministrators")).andExpect(status().isBadRequest());

    }

    @Test
    public void addAdministrator() throws Exception{
        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").username("popescuandrei").build();

        doNothing().when(administratorService).addAdministrator(administrator);

        restMockMvc.perform(post("/api/v1/administrator/addAdministrator").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(administrator))).andExpect(status().isOk());
    }

    @Test
    public void addAdministratorBadRequest() throws Exception{

        doThrow(AdministratorWasFoundException.class).when(administratorService).addAdministrator(Administrator.builder().fullName("test").username("test").email("test").build());

        restMockMvc.perform(post("/api/v1/administrator/addAdministrator").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(Administrator.builder().fullName("test").username("test").email("test").build()))).andExpect(status().isBadRequest());

    }

    @Test
    public void removeAdministrator() throws Exception{
        doNothing().when(administratorService).removeAdministrator("andrei");

        restMockMvc.perform(delete("/api/v1/administrator/deleteAdministrator?fullName=andrei")).andExpect(status().isOk());

    }

    @Test
    public void removeAdministratorBadRequest() throws Exception{
        doThrow(AdministratorNotFoundException.class).when(administratorService).removeAdministrator("andrei");

        restMockMvc.perform(delete("/api/v1/administrator/deleteAdministrator?fullName=andrei")).andExpect(status().isBadRequest());

    }

    @Test
    public void getAdministratorByFullName()throws Exception{
        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").username("popescuandrei").build();

        doReturn(administrator).when(administratorService).getAdministratorByFullName("Popescu Andrei");

        restMockMvc.perform(get("/api/v1/administrator/getAdministratorByFullName?fullName=Popescu Andrei")).andExpect(status().isOk());

    }

    @Test
    public void getAdministratorByFullNameBadRequest()throws Exception{

        doThrow(AdministratorNotFoundException.class).when(administratorService).getAdministratorByFullName("test");

        restMockMvc.perform(get("/api/v1/administrator/getAdministratorByFullName?fullName=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getAdministratorById() throws Exception{
        Administrator administrator = Administrator.builder().id(1L).fullName("Popescu Andrei").email("popescuandrei@gmail.com").username("popescuandrei").build();

        doReturn(administrator).when(administratorService).getAdministratorById(1);


        restMockMvc.perform(get("/api/v1/administrator/getAdministratorById/1")).andExpect(status().isOk());


    }

    @Test
    public void getAdministratorByIdBadRequest() throws Exception{
        doThrow(AdministratorNotFoundException.class).when(administratorService).getAdministratorById(1);


        restMockMvc.perform(get("/api/v1/administrator/getAdministratorById/1")).andExpect(status().isBadRequest());
    }

    @Test
    public void getAdministratorByUsername() throws Exception{
        Administrator administrator = Administrator.builder().id(1L).fullName("Popescu Andrei").email("popescuandrei@gmail.com").username("popescuandrei").build();

        doReturn(administrator).when(administratorService).getAdministratorByUsername("popescuandrei");

        restMockMvc.perform(get("/api/v1/administrator/getAdministratorByUsername?username=popescuandrei")).andExpect(status().isOk());

    }

    @Test
    public void getAdministratorByUsernameBadRequest() throws Exception{

        doThrow(AdministratorNotFoundException.class).when(administratorService).getAdministratorByUsername("test");

        restMockMvc.perform(get("/api/v1/administrator/getAdministratorByUsername?username=test")).andExpect(status().isBadRequest());

    }


    }



