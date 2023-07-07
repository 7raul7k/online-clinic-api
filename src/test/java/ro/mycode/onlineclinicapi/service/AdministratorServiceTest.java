package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.exceptions.AdministratorNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.AdministratorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Administrator;
import ro.mycode.onlineclinicapi.repo.AdministratorRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdministratorServiceTest {

    @Mock
    AdministratorRepo adminRepo;

    @InjectMocks
    AdministratorService administratorService;

    @Captor
    ArgumentCaptor<Administrator> argumentCaptor;

    @Test
    public void getAllAdministrator(){

        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").dob(LocalDate.of(2023,1,1)).username("popescuandrei").build();
        Administrator administrator1 = Administrator.builder().fullName("Ionescu Razvan").email("ionescurazvan@gmail.com").dob(LocalDate.of(2023,2,3)).username("ionescurazvan").build();
        Administrator administrator2 = Administrator.builder().fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();
        Administrator administrator3 = Administrator.builder().fullName("Stanciu Ionut").email("stanciuionut@gmail.com").dob(LocalDate.of(2023,3,1)).username("stanciuIonut").build();


        List<Administrator> administratorList = new ArrayList<>();

        administratorList.add(administrator);
        administratorList.add(administrator1);
        administratorList.add(administrator2);
        administratorList.add(administrator3);

        doReturn(administratorList).when(adminRepo).getAllAdmin();

        assertEquals(administratorList,this.administratorService.getAllAdministrators());


    }

    @Test
    public void getAllAdministratorException(){

        doReturn(new ArrayList<>()).when(adminRepo).getAllAdmin();

        assertThrows(ListEmptyException.class,()->{
            this.administratorService.getAllAdministrators();
        });
    }

    @Test
    public void addAdministrator(){
        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").dob(LocalDate.of(2023,1,1)).username("popescuandrei").build();

        doReturn(Optional.empty()).when(adminRepo).getAdminByFullName(administrator.getFullName());

        this.administratorService.addAdministrator(administrator);

        verify(adminRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),administrator);
    }

    @Test
    public void addAdministratorException(){

        doReturn(Optional.of(new Administrator())).when(adminRepo).getAdminByFullName("test");

        assertThrows(AdministratorWasFoundException.class,()->{
            this.administratorService.addAdministrator(Administrator.builder().fullName("test").build());
        });
    }

    @Test
    public void removeAdministrator(){
        Administrator administrator1 = Administrator.builder().id(1L).fullName("Ionescu Razvan").email("ionescurazvan@gmail.com").dob(LocalDate.of(2023,2,3)).username("ionescurazvan").build();

        doReturn(Optional.of(administrator1)).when(adminRepo).getAdminByFullName(administrator1.getFullName());

        this.administratorService.removeAdministrator(administrator1.getFullName());

        verify(adminRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),administrator1);

    }

    @Test
    public void removeAdministratorException(){

        doReturn(Optional.empty()).when(adminRepo).getAdminByFullName("test");

        assertThrows(AdministratorNotFoundException.class,()->{
           this.administratorService.removeAdministrator("test");
        });
    }

    @Test
    public void getAdministratorByFullName(){
        Administrator administrator2 = Administrator.builder().fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();

        doReturn(Optional.of(administrator2)).when(adminRepo).getAdminByFullName("Popa Cristian");

        assertEquals(administrator2,this.administratorService.getAdministratorByFullName("Popa Cristian"));
    }

    @Test
    public void getAdministratorByFullNameException(){

        doReturn(Optional.empty()).when(adminRepo).getAdminByFullName("test");

        assertThrows(AdministratorNotFoundException.class,()->{
            this.administratorService.getAdministratorByFullName("test");
        });
    }

    @Test
    public void getAdministratorbyId(){
        Administrator administrator = Administrator.builder().id(1L).fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();

        doReturn(Optional.of(administrator)).when(adminRepo).getAdminById(1);

        assertEquals(administrator,this.administratorService.getAdministratorById(1));

    }

    @Test
    public void getAdministratorByIdException(){

        doReturn(Optional.empty()).when(adminRepo).getAdminById(1L);

        assertThrows(AdministratorNotFoundException.class,()->{
            this.administratorService.getAdministratorById(1);
        });
    }

    @Test
    public void getAdministratorByUsername(){
        Administrator administrator = Administrator.builder().id(1L).fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();

        doReturn(Optional.of(administrator)).when(adminRepo).getAdminByUsername("popacristian");

        assertEquals(administrator,this.administratorService.getAdministratorByUsername("popacristian"));
    }

    @Test
    public void getAdministratorByUsernameException(){

        doReturn(Optional.empty()).when(adminRepo).getAdminByUsername("test");

        assertThrows(AdministratorNotFoundException.class,()->{
            this.administratorService.getAdministratorByUsername("test");
        });
    }
}