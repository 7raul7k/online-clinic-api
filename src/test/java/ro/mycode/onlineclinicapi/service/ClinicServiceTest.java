package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.ClinicDTO;
import ro.mycode.onlineclinicapi.exceptions.ClinicNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.ClinicWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Clinic;
import ro.mycode.onlineclinicapi.repo.ClinicRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClinicServiceTest {

    @Mock
    private ClinicRepo clinicRepo;

    @InjectMocks
    private ClinicService clinicService;

    @Captor
    ArgumentCaptor<Clinic> argumentCaptor;

    @Test
    public void getAllClinic(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test1").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test2").type("clinic").description("test2").build();


        List<Clinic> clinicList = new ArrayList<>();

        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);


        doReturn(clinicList).when(clinicRepo).getAllClinic();

        assertEquals(clinicList,this.clinicService.getAllClinic());

    }

    @Test
    public void getAllClinicException(){
        doReturn(new ArrayList<>()).when(clinicRepo).getAllClinic();

        assertThrows(ListEmptyException.class,()->{
            this.clinicService.getAllClinic();
        });
    }

    @Test
    public void addClinic(){
        ClinicDTO clinicDTO = ClinicDTO.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doReturn(Optional.empty()).when(clinicRepo).getClinicByName(clinic.getName());

        this.clinicService.addClinic(clinicDTO);

        verify(clinicRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),clinic);
    }

    @Test
    public void addClinicException(){
        doReturn(Optional.of(new Clinic())).when(clinicRepo).getClinicByName("test");

        assertThrows(ClinicWasFoundException.class,()->{
            this.clinicService.addClinic(ClinicDTO.builder().name("test").build());
        });
    }

    @Test
    public void removeClinic(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doReturn(Optional.of(clinic)).when(clinicRepo).getClinicByName(clinic.getName());

        this.clinicService.removeClinic(clinic.getName());

        verify(clinicRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),clinic);
    }

    @Test
    public void removeClinicException(){
        doReturn(Optional.empty()).when(clinicRepo).getClinicByName("test");

        assertThrows(ClinicNotFoundException.class,()->{
            this.clinicService.removeClinic("test");
        });
    }

    @Test
    public void getClinicByName(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doReturn(Optional.of(clinic)).when(clinicRepo).getClinicByName(clinic.getName());

        assertEquals(clinic,this.clinicService.getClinicByName(clinic.getName()));
    }

    @Test
    public void getClinicByNameException(){
        doReturn(Optional.empty()).when(clinicRepo).getClinicByName("test");

        assertThrows(ClinicNotFoundException.class,()->{
            this.clinicService.getClinicByName("test");
        });
    }

    @Test
    public void getClinicByPlace(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test").type("clinic").description("test2").build();



        List<Clinic> clinicList = new ArrayList<>();
        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);

        doReturn(clinicList).when(clinicRepo).getClinicByPlace(clinic.getPlace());

        assertEquals(clinicList,this.clinicService.getClinicByPlace(clinic1.getPlace()));
    }

    @Test
    public void getClinicByPlaceException(){
        doReturn(new ArrayList<>()).when(clinicRepo).getClinicByPlace("test");

        assertThrows(ListEmptyException.class,()->{
            this.clinicService.getClinicByPlace("test");
        });
    }

    @Test
    public void getClinicByType(){
        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();
        Clinic clinic1 = Clinic.builder().name("test1").address("Iasi").place("test").type("clinic").description("test1").build();
        Clinic clinic2 = Clinic.builder().name("test2").address("Baia Mare").place("test").type("clinic").description("test2").build();



        List<Clinic> clinicList = new ArrayList<>();
        clinicList.add(clinic);
        clinicList.add(clinic1);
        clinicList.add(clinic2);

        doReturn(clinicList).when(clinicRepo).getClinicByType("test");

        assertEquals(clinicList,this.clinicService.getClinicByType("test"));
    }

    @Test
    public void getClinicByTypeException(){
     doReturn(new ArrayList<>()).when(clinicRepo).getClinicByType("test");

     assertThrows(ListEmptyException.class,()->{
         this.clinicService.getClinicByType("test");
     });
    }

    @Test
    public void updateClinic(){
        ClinicDTO clinicDTO = ClinicDTO.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        Clinic clinic = Clinic.builder().name("test").address("Bucuresti").place("test").type("clinic").description("test").build();

        doReturn(Optional.of(clinic)).when(clinicRepo).getClinicByName("test");

        this.clinicService.updateClinic(clinicDTO);

        verify(clinicRepo,times(1)).saveAndFlush(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),clinic);

    }

    @Test
    public void updateClinicException(){
        doReturn(Optional.empty()).when(clinicRepo).getClinicByName("test");

        assertThrows(ClinicNotFoundException.class,()->{
            this.clinicService.updateClinic(ClinicDTO.builder().name("test").build());
        });
    }



}