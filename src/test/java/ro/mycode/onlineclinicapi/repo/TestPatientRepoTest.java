package ro.mycode.onlineclinicapi.repo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.TestPatient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional
class TestPatientRepoTest {

    @Autowired
    public TestPatientRepo testPatientRepo;

    @BeforeEach
    public void clean(){this.testPatientRepo.deleteAll();}

    @Test
    public void getAllTest(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test1").type("test1").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test2").type("test2").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test3").type("test3").build();

        testPatientRepo.save(testPatient);
        testPatientRepo.save(testPatient1);
        testPatientRepo.save(testPatient2);
        testPatientRepo.save(testPatient3);

        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        assertEquals(testPatientList,this.testPatientRepo.getAllTest());

    }

    @Test
    public void getTestByType(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test1").type("test").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test2").type("test").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test3").type("test").build();

        testPatientRepo.save(testPatient);
        testPatientRepo.save(testPatient1);
        testPatientRepo.save(testPatient2);
        testPatientRepo.save(testPatient3);

        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        assertEquals(testPatientList,this.testPatientRepo.getTestByType("test"));
    }

    @Test
    public void getTestByReport(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();
        TestPatient testPatient1 = TestPatient.builder().name("test1").cost(2132).description("test1").report("test").type("test1").build();
        TestPatient testPatient2 = TestPatient.builder().name("test2").cost(2132).description("test2").report("test").type("test2").build();
        TestPatient testPatient3 = TestPatient.builder().name("test3").cost(2132).description("test3").report("test").type("test3").build();

        testPatientRepo.save(testPatient);
        testPatientRepo.save(testPatient1);
        testPatientRepo.save(testPatient2);
        testPatientRepo.save(testPatient3);

        List<TestPatient> testPatientList =  new ArrayList<>();
        testPatientList.add(testPatient);
        testPatientList.add(testPatient1);
        testPatientList.add(testPatient2);
        testPatientList.add(testPatient3);

        assertEquals(testPatientList,this.testPatientRepo.getTestByReport("test"));

    }

    @Test
    public void getTestByName(){
        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        testPatientRepo.save(testPatient);

        assertEquals(testPatient,this.testPatientRepo.getTestByName("test").get());
    }

}