package ro.mycode.onlineclinicapi.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.mycode.onlineclinicapi.dto.TestPatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.TestPatientNotFoundException;
import ro.mycode.onlineclinicapi.models.TestPatient;
import ro.mycode.onlineclinicapi.service.TestPatientService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class TestPatientResourceTest {

    @Mock
    private TestPatientService testPatientService;

    @InjectMocks
    private TestPatientResource testPatientResource;

    public ObjectMapper mapper = new ObjectMapper();

    public MockMvc restMockMvc;

    @BeforeEach
    public void initialConfig(){restMockMvc = MockMvcBuilders.standaloneSetup(testPatientResource).build();}

    @Test
    public void getAllTestPatient() throws Exception{
        Faker faker = new Faker();

        List<TestPatient> testPatientList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            testPatientList.add(TestPatient.builder().id((long) i)
                    .name(faker.lorem().sentence())
                    .type(faker.lorem().sentence())
                    .description(faker.lorem().sentence())
                    .cost(faker.number().randomDouble(3,1,999))
                    .report(faker.lorem().sentence())
                    .build());
        }

        doReturn(testPatientList).when(testPatientService).patientList();

        restMockMvc.perform(get("/api/v1/testPatient/getAllTestPatient")).andExpect(status().isOk());
    }

    @Test
    public void getAllTestPatientBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(testPatientService).patientList();

        restMockMvc.perform(get("/api/v1/testPatient/getAllTestPatient")).andExpect(status().isBadRequest());

    }

    @Test
    public void addTestPatient() throws Exception{
        TestPatientDTO testPatientDTO = TestPatientDTO.builder().name("test").cost(2132).description("test").report("test").type("test").build();

        doNothing().when(testPatientService).addTestPatient(testPatientDTO);

        restMockMvc.perform(post("/api/v1/testPatient/addTest").content(mapper.writeValueAsBytes(testPatientDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    public void removeTest() throws Exception{


        doNothing().when(testPatientService).deleteTestPatient("test");

        restMockMvc.perform(delete("/api/v1/testPatient/deleteTestPatient?testName=test")).andExpect(status().isOk());
    }

    @Test
    public void removeTestBadRequest() throws Exception{
        doThrow(TestPatientNotFoundException.class).when(testPatientService).deleteTestPatient("test");

        restMockMvc.perform(delete("/api/v1/testPatient/deleteTestPatient?testName=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void  getTestPatientByName() throws Exception{

        TestPatient testPatient = TestPatient.builder().name("test").cost(2132).description("test").report("test").type("test").build();


        doReturn(testPatient).when(testPatientService).getTestPatientbyName(testPatient.getName());

        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByName?name=test")).andExpect(status().isOk());


    }

    @Test
    public void getTestPatientByNameBadRequest() throws Exception{

        doThrow(TestPatientNotFoundException.class).when(testPatientService).getTestPatientbyName("test");

        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByName?name=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void getTestPatientByType() throws Exception{
       Faker faker = new Faker();

       List<TestPatient> testPatientList = new ArrayList<>();

       for(int i = 0 ; i < 10 ; i++){
           testPatientList.add(TestPatient.builder().id((long) i)
                   .cost(faker.number().randomDouble(1,1,999))
                   .description(faker.lorem().sentence())
                   .type("clinic")
                   .name(faker.lorem().sentence())
                   .report(faker.lorem().sentence())
                   .build());
       }

        doReturn(testPatientList).when(testPatientService).getTestbyType("clinic");

        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByType?type=clinic")).andExpect(content().string(mapper.writeValueAsString(testPatientList)));

    }


    @Test
    public void getTestPatientByTypeBadRequest() throws Exception{

        doThrow(TestPatientNotFoundException.class).when(testPatientService).getTestbyType("clinic");

        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByType?type=clinic")).andExpect(status().isBadRequest());

    }

    @Test
    public void getTestPatientByReport() throws Exception{
        Faker faker = new Faker();

        List<TestPatient> testPatientList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            testPatientList.add(TestPatient.builder().id((long) i)
                    .cost(faker.number().randomDouble(1,1,999))
                    .description(faker.lorem().sentence())
                    .type(faker.lorem().sentence())
                    .name(faker.lorem().sentence())
                    .report("test")
                    .build());
        }

        doReturn(testPatientList).when(testPatientService).getTestByReport("test");
        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByReport?report=test")).andExpect(status().isOk());

    }

    @Test
    public void getTestPatientByReportBadRequest() throws Exception{


        doThrow(ListEmptyException.class).when(testPatientService).getTestByReport("test");
        restMockMvc.perform(get("/api/v1/testPatient/getTestPatientByReport?report=test")).andExpect(status().isBadRequest());

    }

    @Test
    public void updateTest() throws Exception{
        TestPatientDTO testPatientDTO = TestPatientDTO.builder().name("test").cost(2132).description("test1").report("test").type("test1").build();


        doNothing().when(testPatientService).updateTest(testPatientDTO);

        restMockMvc.perform(put("/api/v1/testPatient/updateTestPatient").content(mapper.writeValueAsString(testPatientDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }


}