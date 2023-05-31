package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.RoleNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.RoleWasFoundException;
import ro.mycode.onlineclinicapi.models.Role;
import ro.mycode.onlineclinicapi.repo.RoleRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepo roleRepo;

    @InjectMocks
    RoleService roleService;

    @Captor
    ArgumentCaptor<Role> argumentCaptor;

    @Test
    public void getAllRole(){
        Role role = Role.builder().title("medical doctor").description("test").build();
        Role role1 = Role.builder().title("Junior doctor").description("test1").build();
        Role role2 = Role.builder().title("Consultant").description("test2").build();


        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        roleList.add(role1);
        roleList.add(role2);

        doReturn(roleList).when(roleRepo).getAllRole();

        assertEquals(roleList,this.roleService.getAllRole());
    }

    @Test
    public void getAllRoleException(){
        doReturn(new ArrayList<>()).when(roleRepo).getAllRole();

        assertThrows(ListEmptyException.class,()->{
            this.roleService.getAllRole();
        });
    }

    @Test
    public void addRole(){
        Role role = Role.builder().title("medical doctor").description("test").build();

        doReturn(Optional.empty()).when(roleRepo).getRoleByTitle(role.getTitle());

        this.roleService.addRole(role);

        verify(roleRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),role);

    }

    @Test
    public void addRoleException(){
        doReturn(Optional.of(new Role())).when(roleRepo).getRoleByTitle("test");

        assertThrows(RoleWasFoundException.class,()->{
            this.roleService.addRole(Role.builder().title("test").build());
        });
    }

    @Test
    public void removeRole(){
        Role role = Role.builder().title("medical doctor").description("test").build();

        doReturn(Optional.of(role)).when(roleRepo).getRoleByTitle(role.getTitle());

        this.roleService.removeRole(role.getTitle());

        verify(roleRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),role);

    }

    @Test
    public void removeRoleException(){
        doReturn(Optional.empty()).when(roleRepo).getRoleByTitle("test");

        assertThrows(RoleNotFoundException.class,()->{
            this.roleService.removeRole("test");
        });
    }

    @Test
    public void getRoleById(){
        Role role = Role.builder().id(1L).title("medical doctor").description("test").build();

        doReturn(Optional.of(role)).when(roleRepo).getRoleById(1L);

        assertEquals(role,this.roleService.getRoleById(1));

    }

    @Test
    public void getRoleByIdException(){
        doReturn(Optional.empty()).when(roleRepo).getRoleById(1L);

        assertThrows(RoleNotFoundException.class,()->{
            this.roleService.getRoleById(1);
        });
    }

    @Test
    public void getRoleByTitle(){
        Role role = Role.builder().id(1L).title("medical doctor").description("test").build();

        doReturn(Optional.of(role)).when(roleRepo).getRoleByTitle(role.getTitle());

        assertEquals(role,this.roleService.getRoleByTitle("medical doctor"));
    }
}