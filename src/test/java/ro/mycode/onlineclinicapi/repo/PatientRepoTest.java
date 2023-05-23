package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Patient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional
class PatientRepoTest {

    @Autowired
    public PatientRepo patientRepo;

    @BeforeEach
    public void clean(){ patientRepo.deleteAll();}

    @Test
    public void getAllPatient(){

        Patient patient = Patient.builder().fullName("Popescu Ioan").adress("Bucuresti").number("0723949232").email("popescuioan@gmail.com").password("popescuioan@gmail.com2023").username("popescuioan").build();
        Patient patient1 = Patient.builder().fullName("Ionescu Radu").adress("Arad").number("0733822382").email("ionescuradu@gmail.com").password("ionescuradu@gmail.com2023").username("raduradu2023").build();
        Patient patient2 = Patient.builder().fullName("Suciu Andrei").adress("Timisoara").number("0758932325").email("suciuandrei@gmail.com").password("suciuandrei@gmail.com2023").username("andiandrei").build();

        patientRepo.save(patient);
        patientRepo.save(patient1);
        patientRepo.save(patient2);

        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);
        patientList.add(patient1);
        patientList.add(patient2);

        assertEquals(patientList,this.patientRepo.getAllPatient());

    }

    @Test
    public void getPatientById(){
        Patient patient = Patient.builder().fullName("Cristi Dinescu").adress("Baia Mare").number("0758932325").email("cristidinescu@gmail.com").password("cristidinescu@gmail.com2023").username("cristicristian").build();

        patientRepo.save(patient);


        assertEquals(patient,this.patientRepo.getPatientById(1).get());

    }

    @Test
    public void getPatientByUsername(){
        Patient patient = Patient.builder().fullName("Sergiu Stan ").adress("Timisoara").number("0758932325").email("sergiustan@gmail.com").password("sergiustan@gmail.com2023").username("sergiusergiu").build();

        patientRepo.save(patient);

        assertEquals(patient,this.patientRepo.getPatientByUsername("sergiusergiu").get());

    }

    @Test
    public void getPatientByEmail(){
        Patient patient = Patient.builder().fullName("George Matei").adress("Bucuresti").number("0758932325").email("georgematei@gmail.com").password("georgematei@gmail.com2023").username("geogeorgematei").build();

        patientRepo.save(patient);

        assertEquals(patient,this.patientRepo.getPatientByEmail("georgematei@gmail.com").get());

    }

    @Test
    public void getPatientByFullName(){
        Patient patient = Patient.builder().fullName("Igor Stănescu").adress("Brasov").number("0758932325").email("igorstanescu@gmail.com").password("igorstanescu@gmail.com2023").username("igorstanescu").build();

        patientRepo.save(patient);

        assertEquals(patient,this.patientRepo.getPatientByFullName("Igor Stănescu").get());


    }

}