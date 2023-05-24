package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Username;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional
class UsernameRepoTest {

    @Autowired
    public UsernameRepo usernameRepo;

    @BeforeEach
    public void clean(){usernameRepo.deleteAll();}

    @Test
    public void getAllUsername(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();
        Username username1 = Username.builder().name("test1").address("test1").email("test1").dob(LocalDate.now()).build();
        Username username2 = Username.builder().name("test2").address("test2").email("test2").dob(LocalDate.now()).build();
        Username username3 = Username.builder().name("test3").address("test3").email("test3").dob(LocalDate.now()).build();

        usernameRepo.save(username);
        usernameRepo.save(username1);
        usernameRepo.save(username2);
        usernameRepo.save(username3);

        List<Username> usernameList = new ArrayList<>();
        usernameList.add(username);
        usernameList.add(username1);
        usernameList.add(username2);
        usernameList.add(username3);

        assertEquals(usernameList,this.usernameRepo.getAllUsernames());




    }


    @Test
    public void getUsernameByName(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();


        usernameRepo.save(username);

        assertEquals(username,this.usernameRepo.getUsernameByName("test").get());

    }

    @Test
    public void getUsernameByAddress(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();
        Username username1 = Username.builder().name("test1").address("test").email("test1").dob(LocalDate.now()).build();
        Username username2 = Username.builder().name("test2").address("test").email("test2").dob(LocalDate.now()).build();
        Username username3 = Username.builder().name("test3").address("test").email("test3").dob(LocalDate.now()).build();

        usernameRepo.save(username);
        usernameRepo.save(username1);
        usernameRepo.save(username2);
        usernameRepo.save(username3);

        List<Username> usernameList = new ArrayList<>();
        usernameList.add(username);
        usernameList.add(username1);
        usernameList.add(username2);
        usernameList.add(username3);

        assertEquals(usernameList,this.usernameRepo.getUsernameByAddress("test"));
    }

    @Test
    public void getUsernameByEmail(){
        Username username = Username.builder().name("test").address("test").email("test").dob(LocalDate.now()).build();


        usernameRepo.save(username);

        assertEquals(username,this.usernameRepo.getUsernameByEmail("test").get());


    }

}