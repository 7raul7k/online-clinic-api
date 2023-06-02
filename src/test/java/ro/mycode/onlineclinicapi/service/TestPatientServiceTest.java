package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.TestPatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.TestPatientNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.TestPatientWasFoundException;
import ro.mycode.onlineclinicapi.models.TestPatient;
import ro.mycode.onlineclinicapi.models.Username;
import ro.mycode.onlineclinicapi.repo.TestPatientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TestPatientServiceTest {

    @Mock
    private TestPatientRepo testPatientRepo;

    @InjectMocks
    private TestPatientService testPatientService;

    @Captor
    ArgumentCaptor<TestPatient> argumentCaptor;

    @Test
    public void getAllTestPatient(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test1").type("test1").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test2").type("test2").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test3").type("test3").build();

        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        doReturn(testPatientList).when(testPatientRepo).getAllTest();

        assertEquals(testPatientList,this.testPatientService.patientList());
    }

    @Test
    public void getAllTestPatientException(){
        doReturn(new ArrayList<>()).when(testPatientRepo).getAllTest();

        assertThrows(ListEmptyException.class,()->{
            this.testPatientService.patientList();
        });
    }

    @Test
    public void addTestPatient(){
        TestPatientDTO testPatientDTO = TestPatientDTO.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        doReturn(Optional.empty()).when(testPatientRepo).getTestByName(testPatient.getName());

        this.testPatientService.addTestPatient(testPatientDTO);

        verify(testPatientRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),testPatient);
    }

    @Test
    public void addTestPatientException(){
        doReturn(Optional.of(new Username())).when(testPatientRepo).getTestByName("test");

        assertThrows(TestPatientWasFoundException.class,()->{
            this.testPatientService.addTestPatient(TestPatientDTO.builder().name("test").build());
        });

    }

    @Test
    public void removeTestPatient(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        doReturn(Optional.of(testPatient)).when(testPatientRepo).getTestByName(testPatient.getName());

        this.testPatientService.deleteTestPatient(testPatient.getName());

        verify(testPatientRepo,times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),testPatient);


    }

    @Test
    public void removeTestPatientException(){
        doReturn(Optional.empty()).when(testPatientRepo).getTestByName("test");

        assertThrows(TestPatientNotFoundException.class,()->{
            this.testPatientService.deleteTestPatient("test");
        });
    }

    @Test
    public void getTestPatientByName(){

        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        doReturn(Optional.of(testPatient)).when(testPatientRepo).getTestByName(testPatient.getName());

        assertEquals(testPatient,this.testPatientService.getTestPatientbyName("test"));

    }

    @Test
    public void getTestPatientByNameError(){

        doReturn(Optional.empty()).when(testPatientRepo).getTestByName("test");

        assertThrows(TestPatientNotFoundException.class,()->{
            this.testPatientService.getTestPatientbyName("test");
        });

    }

    @Test
    public void getTestPatientbyType(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test1").type("test").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test2").type("test").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test3").type("test").build();


        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        doReturn(testPatientList).when(testPatientRepo).getTestByType("test");

        assertEquals(testPatientList,this.testPatientService.getTestbyType("test"));

    }

    @Test
    public void getTestPatientByTypeException(){
        doReturn(new ArrayList<>()).when(testPatientRepo).getTestByType("test");

        assertThrows(ListEmptyException.class,()->{
            this.testPatientService.getTestbyType("test");
        });
    }

    @Test
    public void getTestPatientByReport(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test").type("test1").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test").type("test2").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test").type("test3").build();


        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        doReturn(testPatientList).when(testPatientRepo).getTestByReport("test");

        assertEquals(testPatientList,this.testPatientService.getTestByReport("test"));
    }

    @Test
    public void getTestPatientByReportException(){
        doReturn(new ArrayList<>()).when(testPatientRepo).getTestByReport("test");

        assertThrows(ListEmptyException.class,()->{
            this.testPatientService.getTestByReport("test");
        });
    }

    @Test
    public void updateTestPatient(){

        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatientDTO testPatientDTO = TestPatientDTO.builder().name("test").cost(2132).description("test1").report("test").type("test1").build();


        doReturn(Optional.of(testPatient)).when(testPatientRepo).getTestByName("test");

        this.testPatientService.updateTest(testPatientDTO);

        verify(testPatientRepo,times(1)).saveAndFlush(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),testPatient);
    }

}