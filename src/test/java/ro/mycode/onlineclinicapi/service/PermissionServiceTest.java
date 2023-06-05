package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.PermissionDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PermissionNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PermissionWasFoundException;
import ro.mycode.onlineclinicapi.models.Permission;
import ro.mycode.onlineclinicapi.repo.PermissionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    public PermissionRepo permissionRepo;

    @InjectMocks
    public PermissionService permissionService;

    @Captor
    ArgumentCaptor<Permission> argumentCaptor;

    @Test
    public void getAllPermission(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();
        Permission permission1 = Permission.builder().title("test1").description("test1").module("test1").build();
        Permission permission2 = Permission.builder().title("test2").description("test2").module("test2").build();

        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(permission);
        permissionList.add(permission1);
        permissionList.add(permission2);

        doReturn(permissionList).when(permissionRepo).getAllPermision();

        assertEquals(permissionList,this.permissionService.getAllPermission());

    }

    @Test
    public void getAllPermissionException(){
        doReturn(new ArrayList<>()).when(permissionRepo).getAllPermision();

        assertThrows(ListEmptyException.class,()->{
            this.permissionService.getAllPermission();

        });
    }

    @Test
    public void addPermission(){
        PermissionDTO permission = PermissionDTO.builder().title("test").description("test").module("test").build();

        doReturn(Optional.empty()).when(permissionRepo).getPermissionByTitle(permission.getTitle());

        this.permissionService.addPermission(permission);

         verify(permissionRepo,times(1)).save(argumentCaptor.capture());

         assertEquals(argumentCaptor.getValue(),permission);
    }

    @Test
    public void addPermissionException(){
        doReturn(Optional.of(new Permission())).when(permissionRepo).getPermissionByTitle("test");

        assertThrows(PermissionWasFoundException.class,()->{
            this.permissionService.addPermission(PermissionDTO.builder().title("test").build());
        });
    }

    @Test
    public void removePermission(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        doReturn(Optional.of(permission)).when(permissionRepo).getPermissionByTitle(permission.getTitle());

        this.permissionService.removePermission(permission.getTitle());

        verify(permissionRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),permission);
    }

    @Test
    public void removePermissionException(){
        doReturn(Optional.empty()).when(permissionRepo).getPermissionByTitle("test");

        assertThrows(PermissionNotFoundException.class,()->{
            this.permissionService.removePermission("test");
        });
    }

    @Test
    public void getPermissionByTitle(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();


        doReturn(Optional.of(permission)).when(permissionRepo).getPermissionByTitle(permission.getTitle());

        assertEquals(permission,this.permissionService.getPermissionByTitle("test"));

    }

    @Test
    public void getPermissionByTitleException(){
        doReturn(Optional.empty()).when(permissionRepo).getPermissionByTitle("test");

        assertThrows(PermissionNotFoundException.class,()->{
            this.permissionService.getPermissionByTitle("test");
        });
    }

    @Test
    public void getPermissionByModule(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        doReturn(Optional.of(permission)).when(permissionRepo).getPermissionByModule("test");

        assertEquals(permission,this.permissionService.getPermissionbyModule("test"));
    }

    @Test
    public void getPermissionByModuleException(){
        doReturn(Optional.empty()).when(permissionRepo).getPermissionByModule("test");


        assertThrows(PermissionNotFoundException.class,()->{
            this.permissionService.getPermissionbyModule("test");
        });
    }

    @Test
    public void updatePermission(){
        PermissionDTO permissionDTO = PermissionDTO.builder().title("test").description("test").module("test").build();
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        doReturn(Optional.of(permission)).when(permissionRepo).getPermissionByTitle("test");

        this.permissionService.updatePermission(permissionDTO);

        verify(permissionRepo,times(1)).saveAndFlush(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),permission);


    }

    @Test
    public void updatePermissionException(){
        doReturn(Optional.empty()).when(permissionRepo).getPermissionByTitle("test");

        assertThrows(PermissionNotFoundException.class,()->{
            this.permissionService.updatePermission(PermissionDTO.builder().title("test").build());
        });
    }
}