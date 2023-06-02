package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.DoctorDTO;
import ro.mycode.onlineclinicapi.exceptions.DoctorNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.DoctorWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.repo.DoctorRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    DoctorRepo doctorRepo;

    @InjectMocks
    DoctorService doctorService;

    @Captor
    ArgumentCaptor<Doctor> argumentCaptor;

    @Test
    public void getAllDoctor(){
        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();
        Doctor doctor1 = Doctor.builder().fullName("Adela Crăciun").adress("Timisoara").email("adelacraciun@gmail.com").password("adelacraciun@gmail.com2023").username("adelaade").mobile("0732821912").build();
        Doctor doctor2 = Doctor.builder().fullName("Daniel Ghiță").adress("Sibiu").email("danielghita@gmail.com").password("danielghita@gmail.com2023").username("danidaniel").mobile("0782239184").build();


        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor1);
        doctorList.add(doctor2);

        doReturn(doctorList).when(doctorRepo).getAllDoctor();

        assertEquals(doctorList,this.doctorService.getAllDoctor());
    }

    @Test
    public void getAllDoctorException(){
        doReturn(new ArrayList<>()).when(doctorRepo).getAllDoctor();

        assertThrows(ListEmptyException.class,()->{
            this.doctorService.getAllDoctor();
        });
    }

    @Test
    public void addDoctor(){

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();


        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(Optional.empty()).when(doctorRepo).getDoctorByFullName(doctorDTO.getFullName());

        this.doctorService.addDoctor(doctorDTO);

        verify(doctorRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),doctor);
    }

    @Test
    public void addDoctorException(){
        doReturn(Optional.of(new Patient())).when(doctorRepo).getDoctorByFullName("test");

        assertThrows(DoctorWasFoundException.class,()->{
            this.doctorService.addDoctor(DoctorDTO.builder().fullName("test").build());
        });
    }

    @Test
    public void removeDoctor(){

        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(Optional.of(doctor)).when(doctorRepo).getDoctorByFullName(doctor.getFullName());

        this.doctorService.removeDoctor(doctor.getFullName());

        verify(doctorRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),doctor);

    }

    @Test
    public void removeDoctorException(){
        doReturn(Optional.empty()).when(doctorRepo).getDoctorByFullName("test");

        assertThrows(DoctorNotFoundException.class,()->{
            this.doctorService.removeDoctor("test");
        });
    }

    @Test
    public void getDoctorByUsername(){
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(Optional.of(doctor)).when(doctorRepo).getDoctorByUsername(doctor.getUsername());

        assertEquals(doctor,this.doctorService.getDoctorByUsername("davidmoise"));

    }

    @Test
    public void getDoctorByUsernameException(){
        doReturn(Optional.empty()).when(doctorRepo).getDoctorByUsername("test");

        assertThrows(DoctorNotFoundException.class,()->{
            this.doctorService.getDoctorByUsername("test");
        });
    }

    @Test
    public void getDoctorByEmail(){
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(Optional.of(doctor)).when(doctorRepo).getDoctorByEmail(doctor.getEmail());

        assertEquals(doctor,this.doctorService.getDoctorByEmail("davidmoisescu@gmail.com"));
    }

    @Test
    public void getDoctorByEmailException(){
        doReturn(Optional.empty()).when(doctorRepo).getDoctorByEmail("test");

        assertThrows(DoctorNotFoundException.class,()->{
            this.doctorService.getDoctorByEmail("test");
        });
    }

    @Test
    public void getDoctorByFullName(){
        Doctor doctor = Doctor.builder()
                .fullName("David Moisescu")
                .adress("Bucuresti")
                .email("davidmoisescu@gmail.com")
                .password("avidmoisescu@gmail.com2023")
                .username("davidmoise").mobile("0732952713")
                .build();

        doReturn(Optional.of(doctor)).when(doctorRepo).getDoctorByFullName(doctor.getFullName());

        assertEquals(doctor,this.doctorService.getDoctorByFullName("David Moisescu"));

    }

    @Test
    public void getDoctorByFullNameException(){
        doReturn(Optional.empty()).when(doctorRepo).getDoctorByFullName("test");

        assertThrows(DoctorNotFoundException.class,()->{
            this.doctorService.getDoctorByFullName("test");
        });
    }
}