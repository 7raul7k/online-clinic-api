package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.UsernameDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.UsernameNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.UsernameWasFoundException;
import ro.mycode.onlineclinicapi.models.Username;
import ro.mycode.onlineclinicapi.repo.UsernameRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UsernameServiceTest {

    @Mock
    UsernameRepo usernameRepo;

    @InjectMocks
    UsernameService usernameService;

    @Captor
    ArgumentCaptor<Username> argumentCaptor;

    @Test
    public void getAllUsername(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();
        Username username1 = Username.builder().name("test1").address("test1").email("test1").dob(LocalDate.now()).build();
        Username username2 = Username.builder().name("test2").address("test2").email("test2").dob(LocalDate.now()).build();
        Username username3 = Username.builder().name("test3").address("test3").email("test3").dob(LocalDate.now()).build();

        List<Username> usernameList = new ArrayList<>();
        usernameList.add(username);
        usernameList.add(username1);
        usernameList.add(username2);
        usernameList.add(username3);

        doReturn(usernameList).when(usernameRepo).getAllUsernames();

        assertEquals(usernameList,this.usernameRepo.getAllUsernames());
    }

    @Test
    public void getAllUsernameException(){
        doReturn(new ArrayList<>()).when(usernameRepo).getAllUsernames();

        assertThrows(ListEmptyException.class,()->{
            this.usernameService.getAllUsername();
        });
    }

    @Test
    public void addUsername(){
        UsernameDTO usernameDTO = UsernameDTO.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

        doReturn(Optional.empty()).when(usernameRepo).getUsernameByName(usernameDTO.getName());

        this.usernameService.addUsername(usernameDTO);

        verify(usernameRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),username);


    }

    @Test
    public void addUsernameException(){
        doReturn(Optional.of(new Username())).when(usernameRepo).getUsernameByName("test");

        assertThrows(UsernameWasFoundException.class,()->{
            this.usernameService.addUsername(UsernameDTO.builder().name("test").build());
        });
    }

    @Test
    public void removeUsername(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

        doReturn(Optional.of(username)).when(usernameRepo).getUsernameByName(username.getName());

        this.usernameService.deleteUsername(username.getName());

        verify(usernameRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),username);

    }

    @Test
    public void removeUsernameException(){
        doReturn(Optional.empty()).when(usernameRepo).getUsernameByName("test");

        assertThrows(UsernameNotFoundException.class,()->{
            this.usernameService.deleteUsername("test");
        });
    }

    @Test
    public void getUsernameByName(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

        doReturn(Optional.of(username)).when(usernameRepo).getUsernameByName(username.getName());


        assertEquals(username,this.usernameService.getUsernameByName(username.getName()));
    }

    @Test
    public void getUsernameByNameException(){
        doReturn(Optional.empty()).when(usernameRepo).getUsernameByName("test");

        assertThrows(UsernameNotFoundException.class,()->{
            this.usernameService.getUsernameByName("test");
        });
    }

    @Test
    public void getUsernameByEmail(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();

        doReturn(Optional.of(username)).when(usernameRepo).getUsernameByEmail(username.getEmail());

        assertEquals(username,this.usernameService.getUsernameByEmail(username.getEmail()));
    }

    @Test
    public void getUsernamebyEmailException(){
        doReturn(Optional.empty()).when(usernameRepo).getUsernameByEmail("test");

        assertThrows(UsernameNotFoundException.class,()->{
            this.usernameService.getUsernameByEmail("test");
        });
    }

    @Test
    public void getUsernamebyAddress(){

        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();
        Username username1 = Username.builder().name("test1").address("test").email("test1").dob(LocalDate.now()).build();
        Username username2 = Username.builder().name("test2").address("test").email("test2").dob(LocalDate.now()).build();
        Username username3 = Username.builder().name("test3").address("test").email("test3").dob(LocalDate.now()).build();

        List<Username> usernameList = new ArrayList<>();
        usernameList.add(username);
        usernameList.add(username1);
        usernameList.add(username2);
        usernameList.add(username3);

        doReturn(usernameList).when(usernameRepo).getUsernameByAddress(username.getAddress());

        assertEquals(usernameList,this.usernameService.getAllUsernameByAdress("test"));
    }


    @Test
    public void getUsernameByAdressException(){
        doReturn(new ArrayList<>()).when(usernameRepo).getUsernameByAddress("test");

        assertThrows(ListEmptyException.class,()->{
            this.usernameService.getAllUsernameByAdress("test");
        });
    }





}