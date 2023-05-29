package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.PatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PatientNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PatientWasFoundException;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.repo.PatientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    PatientRepo patientRepo;

    @InjectMocks
    PatientService patientService;

    @Captor
    ArgumentCaptor<Patient> argumentCaptor;

    @Test
    public void getAllPatientOk(){
        Patient patient = Patient.builder().fullName("Popescu Ioan").adress("Bucuresti").number("0723949232").email("popescuioan@gmail.com").password("popescuioan@gmail.com2023").username("popescuioan").build();
        Patient patient1 = Patient.builder().fullName("Ionescu Radu").adress("Arad").number("0733822382").email("ionescuradu@gmail.com").password("ionescuradu@gmail.com2023").username("raduradu2023").build();
        Patient patient2 = Patient.builder().fullName("Suciu Andrei").adress("Timisoara").number("0758932325").email("suciuandrei@gmail.com").password("suciuandrei@gmail.com2023").username("andiandrei").build();

        List<Patient> list = new ArrayList<>();

        list.add(patient);
        list.add(patient1);
        list.add(patient2);

        doReturn(list).when(patientRepo).getAllPatient();

        assertEquals(list,this.patientService.getAllPatient());
    }

    @Test
    public void getAllPatientException(){
        doReturn(new ArrayList<>()).when(patientRepo).getAllPatient();

        assertThrows(ListEmptyException.class,()->{
            this.patientService.getAllPatient();

        });
    }

    @Test
    public void addPatientOk(){

        PatientDTO patientDTO = PatientDTO.builder()
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();



        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.empty()).when(patientRepo).getPatientByFullName(patientDTO.getFullName());

        patientService.addPatient(patientDTO);

        verify(patientRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),patient);

    }

    @Test
    public void addPatientException(){
        doReturn(Optional.of(new Patient())).when(patientRepo).getPatientByFullName("test");

        assertThrows(PatientWasFoundException.class,()->{
            this.patientService.addPatient(PatientDTO.builder().fullName("test").build());
        });
    }

    @Test
    public void removePatientOk(){
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByFullName(patient.getFullName());

        patientService.deletePatient(patient.getFullName());

        verify(patientRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),patient);
    }

    @Test
    public void removePatientException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByFullName("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.deletePatient("test");
        });
    }

    @Test
    public void getPatientById(){

        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientById(1);

        assertEquals(patient,this.patientService.getPatientbyId(1));
    }

    @Test
    public void getPatientByIdException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientById(1L);

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.getPatientbyId(1);
        });
    }

    @Test
    public void getPatientByUsername(){
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByUsername(patient.getUsername());

        assertEquals(patient,this.patientService.getPatientByUsername("cristicristian"));
    }

    @Test
    public void getPatientByUsernameException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByUsername("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.getPatientByUsername("test");
        });
    }

    @Test
    public void getPatientByEmail(){
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByEmail(patient.getEmail());

        assertEquals(patient,this.patientService.getPatientByEmail("cristidinescu@gmail.com"));
    }

    @Test
    public void getPatientByEmailException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByEmail("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.getPatientByEmail("test");
        });

    }

    @Test
    public void getPatientbyFullName(){
        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByFullName(patient.getFullName());

        assertEquals(patient,this.patientService.getPatientbyFullName("Cristi Dinescu"));
    }

    @Test
    public void getPatientByFullNameException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByFullName("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.getPatientbyFullName("test");
        });
    }

    @Test
    public void updatePatient(){
        PatientDTO patientDTO = PatientDTO.builder()
                .fullName("Cristi Dinescu")
                .adress("Sibiu").number("0758322325")
                .email("cristidinescu2023@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();



        Patient patient = Patient.builder().id(1L)
                .fullName("Cristi Dinescu")
                .adress("Baia Mare").number("0758932325")
                .email("cristidinescu@gmail.com")
                .password("cristidinescu@gmail.com2023")
                .username("cristicristian")
                .build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByFullName(patientDTO.getFullName());

        this.patientService.updatePatient(patientDTO);

        verify(patientRepo,times(1)).saveAndFlush(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),patient);
    }

    @Test
    public void updatePatientException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByFullName("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.patientService.updatePatient(PatientDTO.builder().fullName("test").build());
        });
    }
}