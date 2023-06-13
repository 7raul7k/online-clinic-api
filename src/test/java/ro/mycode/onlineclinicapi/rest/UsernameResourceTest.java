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
import ro.mycode.onlineclinicapi.dto.UsernameDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.UsernameNotFoundException;
import ro.mycode.onlineclinicapi.models.Username;
import ro.mycode.onlineclinicapi.service.UsernameService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UsernameResourceTest {

    @Mock
    private UsernameService usernameService;

    @InjectMocks
    private UsernameResource usernameResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig() { restMockMvc = MockMvcBuilders.standaloneSetup(usernameResource).build();}

    @Test
    public void getAllUsername() throws Exception{
        Faker faker = new Faker();


        List<Username> usernameList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            usernameList.add(Username.builder().name(faker.lorem().sentence())
                    .email(faker.lorem().sentence())
                    .address(faker.lorem().sentence())
                    .id((long) i )
                    .build());

        }

        doReturn(usernameList).when(usernameService).getAllUsername();

        restMockMvc.perform(get("/api/v1/username/getAllUsername")).andExpect(status().isOk());
    }

    @Test
    public void getAllUsernameBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(usernameService).getAllUsername();

        restMockMvc.perform(get("/api/v1/username/getAllUsername")).andExpect(status().isBadRequest());

    }

    @Test
    public void addUsername() throws Exception{
        UsernameDTO usernameDTO = UsernameDTO.builder().name("test").address("test").build();

        doNothing().when(usernameService).addUsername(usernameDTO);

        restMockMvc.perform(post("/api/v1/username/addUsername").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(usernameDTO))).andExpect(status().isOk());
    }

    @Test
    public void addUsernameBadRequest() throws Exception{

        doThrow(UsernameNotFoundException.class).when(usernameService).addUsername(new UsernameDTO());

        restMockMvc.perform(post("/api/v1/username/addUsername").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new UsernameDTO()))).andExpect(status().isBadRequest());


    }

    @Test
    public void removeUsername() throws Exception{

        doNothing().when(usernameService).deleteUsername("username");

        restMockMvc.perform(delete("/api/v1/username/removeUsername?name=username")).andExpect(status().isOk());

    }

    @Test
    public void getUsernameByName() throws Exception{
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

       doReturn(username).when(usernameService).getUsernameByName("test");

        restMockMvc.perform(get("/api/v1/username/getUsernameByName?name=test")).andExpect(status().isOk());

    }


    @Test
    public void getUsernameByNameBadRequest() throws Exception{

        doThrow(UsernameNotFoundException.class).when(usernameService).getUsernameByName("test");

        restMockMvc.perform(get("/api/v1/username/getUsernameByName?name=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getUsernameByEmail() throws Exception{

        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

        doReturn(username).when(usernameService).getUsernameByEmail("test");


        restMockMvc.perform(get("/api/v1/username/getUsernameByEmail/test")).andExpect(status().isOk());

    }

    @Test
    public void getUsernameByEmailBadRequest() throws Exception{

        doThrow(UsernameNotFoundException.class).when(usernameService).getUsernameByEmail("test");

        restMockMvc.perform(get("/api/v1/username/getUsernameByEmail/test")).andExpect(status().isBadRequest());


    }

    @Test
    public void updateUsername() throws Exception{
        UsernameDTO usernameDTO = UsernameDTO.builder().name("test").address("test").email("test").build();

        doNothing().when(usernameService).updateUsername(usernameDTO);

        restMockMvc.perform(put("/api/v1/username/updateUsername").content(mapper.writeValueAsString(usernameDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    public void updateUsernameBadRequest() throws Exception{

        UsernameDTO usernameDTO = UsernameDTO.builder().name("test").address("test").email("test").build();


        doThrow(UsernameNotFoundException.class).when(usernameService).updateUsername(usernameDTO);
        restMockMvc.perform(put("/api/v1/username/updateUsername").content(mapper.writeValueAsString(usernameDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

}