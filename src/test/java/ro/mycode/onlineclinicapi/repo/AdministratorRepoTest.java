package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Administrator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional
class AdministratorRepoTest {

    @Autowired
    public AdministratorRepo adminRepo;

    @BeforeEach
    public void clean(){
        adminRepo.deleteAll();}

    @Test
    public void getAllAdmin(){

        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").dob(LocalDate.of(2023,1,1)).username("popescuandrei").build();
        Administrator administrator1 = Administrator.builder().fullName("Ionescu Razvan").email("ionescurazvan@gmail.com").dob(LocalDate.of(2023,2,3)).username("ionescurazvan").build();
        Administrator administrator2 = Administrator.builder().fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();
        Administrator administrator3 = Administrator.builder().fullName("Stanciu Ionut").email("stanciuionut@gmail.com").dob(LocalDate.of(2023,3,1)).username("stanciuIonut").build();

        adminRepo.save(administrator);
        adminRepo.save(administrator1);
        adminRepo.save(administrator2);
        adminRepo.save(administrator3);

        List<Administrator> administratorList = new ArrayList<>();

        administratorList.add(administrator);
        administratorList.add(administrator1);
        administratorList.add(administrator2);
        administratorList.add(administrator3);

        assertEquals(administratorList,adminRepo.getAllAdmin());
    }

    @Test
    public void getAdminByEmail(){
        Administrator administrator = Administrator.builder().fullName("Popescu Andrei").email("popescuandrei@gmail.com").dob(LocalDate.of(2023,1,1)).username("popescuandrei").build();

        adminRepo.save(administrator);

        assertEquals(administrator,this.adminRepo.getAdminByEmail("popescuandrei@gmail.com").get());
    }

    @Test
    public void getAdminByFullName(){

        Administrator administrator = Administrator.builder().fullName("Ionescu Razvan").email("ionescurazvan@gmail.com").dob(LocalDate.of(2023,2,3)).username("ionescurazvan").build();

        adminRepo.save(administrator);

        assertEquals(administrator,this.adminRepo.getAdminByFullName("Ionescu Razvan").get());
    }

    @Test
    public void getAdminById(){
        Administrator administrator = Administrator.builder().fullName("Popa Cristian").email("popacristian@gmail.com").dob(LocalDate.of(2023,2,21)).username("popacristian").build();
        adminRepo.save(administrator);

        assertEquals(administrator,this.adminRepo.getAdminById(1L).get());
    }

}