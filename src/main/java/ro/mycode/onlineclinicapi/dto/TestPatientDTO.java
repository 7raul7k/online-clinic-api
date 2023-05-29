package ro.mycode.onlineclinicapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TestPatientDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String type;
    private String description;;
    private double cost;
    private String report;

}
