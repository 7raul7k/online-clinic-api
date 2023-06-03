package ro.mycode.onlineclinicapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.mycode.onlineclinicapi.models.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PermissionDTO {

    private String title;
    private String module;
    private String description;

    private Role role;

}
