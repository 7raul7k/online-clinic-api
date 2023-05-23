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
import ro.mycode.onlineclinicapi.models.Doctor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional

class DoctorRepoTest {

    @Autowired
    public DoctorRepo doctorRepo;

    @BeforeEach
    public void clean(){ doctorRepo.deleteAll();}

    @Test
    public void getAllDoctor(){
        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();
        Doctor doctor1 = Doctor.builder().fullName("Adela Crăciun").adress("Timisoara").email("adelacraciun@gmail.com").password("adelacraciun@gmail.com2023").username("adelaade").mobile("0732821912").build();
        Doctor doctor2 = Doctor.builder().fullName("Daniel Ghiță").adress("Sibiu").email("danielghita@gmail.com").password("danielghita@gmail.com2023").username("danidaniel").mobile("0782239184").build();

        doctorRepo.save(doctor);
        doctorRepo.save(doctor1);
        doctorRepo.save(doctor2);

        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor1);
        doctorList.add(doctor2);


        assertEquals(doctorList,this.doctorRepo.getAllDoctor());

    }

    @Test
    public void getDoctorByUsername(){
        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();

        doctorRepo.save(doctor);

        assertEquals(doctor,this.doctorRepo.getDoctorByUsername("davidmoise").get());
    }

    @Test
    public void getDoctorByEmail(){
        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();

        doctorRepo.save(doctor);

        assertEquals(doctor,this.doctorRepo.getDoctorByEmail("davidmoisescu@gmail.com").get());

    }

    @Test
    public void getDoctorByClinicName(){

        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").username("daviddavid").build();

        doctorRepo.save(doctor);

        assertEquals(doctor,this.doctorRepo.getDoctorByFullName("David Moisescu").get());


    }

}