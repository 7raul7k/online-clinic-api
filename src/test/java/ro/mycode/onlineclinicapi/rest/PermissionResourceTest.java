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
import ro.mycode.onlineclinicapi.dto.PermissionDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PermissionNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PermissionWasFoundException;
import ro.mycode.onlineclinicapi.models.Permission;
import ro.mycode.onlineclinicapi.service.PermissionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PermissionResourceTest {

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private PermissionResource permissionResource;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockmvc;

    @BeforeEach
    public void initialConfig(){ restMockmvc = MockMvcBuilders.standaloneSetup(permissionResource).build();
    }

    @Test
    public void getAllPermission() throws Exception{

        Faker faker = new Faker();

        List<Permission> permissionList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){

            permissionList.add(Permission.builder().description(faker.lorem().sentence())
                    .module(faker.lorem().sentence())
                    .title(faker.lorem().sentence())
                    .id((long) i)
                    .build());
        }

        doReturn(permissionList).when(permissionService).getAllPermission();


        restMockmvc.perform(get("/api/v1/permission/getAllPermission")).andExpect(status().isOk());
    }

    @Test
    public void getAllPermissionBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(permissionService).getAllPermission();

        restMockmvc.perform(get("/api/v1/permission/getAllPermission")).andExpect(status().isBadRequest());

    }

    @Test
    public void addPermission() throws Exception{
        PermissionDTO permission = PermissionDTO.builder().title("test").description("test").module("test").build();

        doNothing().when(permissionService).addPermission(permission);

        restMockmvc.perform(post("/api/v1/permission/addPermission").content(mapper.writeValueAsString(permission)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    public void addPermissionBadRequest() throws Exception{
        doThrow(PermissionWasFoundException.class).when(permissionService).addPermission(new PermissionDTO());

        restMockmvc.perform(post("/api/v1/permission/addPermission").content(mapper.writeValueAsString(new PermissionDTO())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    public void removePermission() throws Exception{
        doNothing().when(permissionService).removePermission("permission");

        restMockmvc.perform(delete("/api/v1/permission/removePermission?name=permission")).andExpect(status().isOk());

    }

    @Test
    public void removePermissionBadRequest() throws Exception{

        doThrow(PermissionNotFoundException.class).when(permissionService).getPermissionByTitle("test");

        restMockmvc.perform(delete("/api/v1/permission/removePermission?name=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getPermissionByTitle() throws Exception{
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        doReturn(permission).when(permissionService).getPermissionByTitle("test");

        restMockmvc.perform(get("/api/v1/permission/getPermissionByTitle?title=test")).andExpect(status().isOk());

    }

    @Test
    public void getPermissionByTitleBadRequest() throws Exception{

        doThrow(PermissionNotFoundException.class).when(permissionService).getPermissionByTitle("test");

        restMockmvc.perform(get("/api/v1/permission/getPermissionByTitle?title=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getPermissionByModule() throws Exception{
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        doReturn(permission).when(permissionService).getPermissionbyModule(permission.getModule());

        restMockmvc.perform(get("/api/v1/permission/getPermissionByModule?module=test")).andExpect(status().isOk());

    }

    @Test
    public void getPermissionByModuleBadRequest() throws Exception{

        doThrow(PermissionNotFoundException.class).when(permissionService).getPermissionbyModule("test");

        restMockmvc.perform(get("/api/v1/permission/getPermissionByModule?module=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void updatePermission() throws Exception{
        PermissionDTO permissionDTO = PermissionDTO.builder().title("test").description("test").module("test").build();

        doNothing().when(permissionService).updatePermission(permissionDTO);

        restMockmvc.perform(put("/api/v1/permission/updatePermission").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(permissionDTO))).andExpect(status().isOk());

    }

    @Test
    public void updatePermissionBadRequest() throws Exception{

        doThrow(PermissionNotFoundException.class).when(permissionService).updatePermission(new PermissionDTO());

        restMockmvc.perform(put("/api/v1/permission/updatePermission").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new PermissionDTO()))).andExpect(status().isBadRequest());

    }





}