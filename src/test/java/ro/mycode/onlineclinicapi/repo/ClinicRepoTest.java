package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Clinic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes =  OnlineClinicApiApplication.class)
@Transactional
class ClinicRepoTest {

    @Autowired
    public ClinicRepo clinicRepo;

    @BeforeEach
    public void clean(){ clinicRepo.deleteAll();}

    @Test
    public void getAllClinic(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test1").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test2").type("clinic").description("test2").build();

        clinicRepo.save(clinic);
        clinicRepo.save(clinic1);
        clinicRepo.save(clinic2);

        List<Clinic> clinicList = new ArrayList<>();

        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);

        assertEquals(clinicList,this.clinicRepo.getAllClinic());
    }

    @Test
    public void getClinicByPlace(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test").type("clinic").description("test2").build();

        clinicRepo.save(clinic);
        clinicRepo.save(clinic1);
        clinicRepo.save(clinic2);


        List<Clinic> clinicList = new ArrayList<>();
        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);
        assertEquals(clinicList,this.clinicRepo.getClinicByPlace("test"));
    }

    @Test
    public void getClinicByType(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test").type("clinic").description("test2").build();

        clinicRepo.save(clinic);
        clinicRepo.save(clinic1);
        clinicRepo.save(clinic2);


        List<Clinic> clinicList = new ArrayList<>();
        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);

        assertEquals(clinicList,this.clinicRepo.getClinicByType("clinic"));
    }

    @Test
    public void getClinicByName(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        clinicRepo.save(clinic);

        assertEquals(clinic,this.clinicRepo.getClinicByName("test").get());

    }
}