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
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.RoleNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.RoleWasFoundException;
import ro.mycode.onlineclinicapi.models.Role;
import ro.mycode.onlineclinicapi.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class RoleResourceTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleResource roleResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){restMockMvc = MockMvcBuilders.standaloneSetup(roleResource).build();}

    @Test
    public void getAllRole() throws Exception{
        Faker faker = new Faker();

        List<Role> roleList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            roleList.add(Role.builder().id((long) i).title(faker.lorem().sentence()).description(faker.lorem().sentence()).build());
        }

        doReturn(roleList).when(roleService).getAllRole();

        restMockMvc.perform(get("/api/v1/role/getAllRole")).andExpect(status().isOk());
    }

    @Test
    public void getAllRoleBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(roleService).getAllRole();

        restMockMvc.perform(get("/api/v1/role/getAllRole")).andExpect(status().isBadRequest());


    }

    @Test
    public void addRole() throws Exception{
        Role role = Role.builder().title("medical doctor").description("test").build();

        doNothing().when(roleService).addRole(role);

        restMockMvc.perform(post("/api/v1/role/addRole").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(role))).andExpect(status().isOk());
    }

    @Test
    public void addRoleBadRequest() throws Exception{
        Role role = Role.builder().title("Test").description("test").build();
        doThrow(RoleWasFoundException.class).when(roleService).addRole(role);

        restMockMvc.perform(post("/api/v1/role/addRole").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(role))).andExpect(status().isBadRequest());

    }

    @Test
    public void removeRole() throws Exception{

        doNothing().when(roleService).removeRole("title");

        restMockMvc.perform(delete("/api/v1/role/deleteRole/title")).andExpect(status().isOk());
    }

    @Test
    public void removeRoleBadRequest() throws Exception{
        doThrow(RoleNotFoundException.class).when(roleService).removeRole("test");

        restMockMvc.perform(delete("/api/v1/role/deleteRole/test")).andExpect(status().isBadRequest());
    }

    @Test
    public void getRoleById() throws Exception{

        Role role = Role.builder().id(1L).title("medical doctor").description("test").build();

        doReturn(role).when(roleService).getRoleById(1);

        restMockMvc.perform(get("/api/v1/role/getRoleById/1")).andExpect(status().isOk());

    }

    @Test
    public void getRoleByIdBadRequest() throws Exception{
        doThrow(RoleNotFoundException.class).when(roleService).getRoleById(1);

        restMockMvc.perform(get("/api/v1/role/getRoleById/1")).andExpect(status().isBadRequest());


    }

    @Test
    public void getRoleByTitle() throws Exception{
        Role role = Role.builder().id(1L).title("doctor").description("test").build();

        doReturn(role).when(roleService).getRoleByTitle("doctor");


        restMockMvc.perform(get("/api/v1/role/getRoleByTitle/doctor")).andExpect(status().isOk());


    }

    @Test
    public void getRoleByTitleBadRequest() throws Exception{
        doThrow(RoleNotFoundException.class).when(roleService).getRoleByTitle("test");

        restMockMvc.perform(get("/api/v1/role/getRoleByTitle/test")).andExpect(status().isBadRequest());
    }
}