package ro.mycode.onlineclinicapi.service;

import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.dto.TestPatientDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.TestPatientNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.TestPatientWasFoundException;
import ro.mycode.onlineclinicapi.models.TestPatient;
import ro.mycode.onlineclinicapi.repo.TestPatientRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TestPatientService {

    public TestPatientRepo testPatientRepo;

    public TestPatientService(TestPatientRepo testPatientRepo) {
        this.testPatientRepo = testPatientRepo;
    }

    public List<TestPatient> patientList(){
        List<TestPatient> testPatientList = this.testPatientRepo.getAllTest();

        if(testPatientList.isEmpty()){
            throw new ListEmptyException();
        }

        return testPatientList;
    }

    public void addTestPatient(TestPatientDTO testPatientDTO){
        Optional<TestPatient> patientOptional = this.testPatientRepo.getTestByName(testPatientDTO.getName());

        if(patientOptional.isEmpty()){
            TestPatient testPatient = TestPatient.builder().type(testPatientDTO.getType())
                    .description(testPatientDTO.getDescription())
                    .cost(testPatientDTO.getCost())
                    .name(testPatientDTO.getName())
                    .report(testPatientDTO.getReport())
                    .patient(testPatientDTO.getPatient())
                    .build();
            this.testPatientRepo.save(testPatient);
        }else{
            throw new TestPatientWasFoundException();
        }
    }

    public void deleteTestPatient(String testPatientName){
        Optional<TestPatient> patientOptional = this.testPatientRepo.getTestByName(testPatientName);

        if(patientOptional.isEmpty()){
            throw new TestPatientNotFoundException();
        }else{
            this.testPatientRepo.delete(patientOptional.get());
        }

    }

    public TestPatient getTestPatientbyName(String name){
        Optional<TestPatient> testPatientOptional = this.testPatientRepo.getTestByName(name);

        if(testPatientOptional.isEmpty()){
            throw new TestPatientNotFoundException();
        }

        return testPatientOptional.get();
    }

    public List<TestPatient>  getTestbyType(String type){
        List<TestPatient> testPatientList = this.testPatientRepo.getTestByType(type);

        if(testPatientList.isEmpty()){
            throw new ListEmptyException();
        }

        return testPatientList;
    }

    public List<TestPatient> getTestByReport(String report){
        List<TestPatient> testPatientList = this.testPatientRepo.getTestByReport(report);

        if(testPatientList.isEmpty()){
            throw new ListEmptyException();
        }

        return testPatientList;
    }

    public void updateTest(TestPatientDTO testPatientDTO){
        Optional<TestPatient> testPatient = this.testPatientRepo.getTestByName(testPatientDTO.getName());

        if(testPatient.isEmpty()){
            throw new TestPatientNotFoundException();
        }

        if(testPatientDTO.getType() != null){
            testPatient.get().setType(testPatientDTO.getType());
        }else if(testPatientDTO.getDescription() != null){
            testPatient.get().setDescription(testPatientDTO.getDescription());
        }else if(testPatientDTO.getReport() != null){
            testPatient.get().setReport(testPatientDTO.getReport());
        } else if (testPatientDTO.getCost() > 0) {
            testPatient.get().setCost(testPatientDTO.getCost());

        }

        this.testPatientRepo.saveAndFlush(testPatient.get());
    }


}
