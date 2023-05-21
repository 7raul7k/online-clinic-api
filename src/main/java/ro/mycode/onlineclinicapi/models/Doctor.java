package ro.mycode.onlineclinicapi.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Doctor {

    @Id
    @SequenceGenerator(name = "doctors_sequence",
    sequenceName = "doctors_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
    generator = "doctors_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String fullName;
    @Column(name ="mobile",
    nullable = false,
    columnDefinition = "TEXT")
    private String mobile;
    @Column(name ="email",
    nullable = false,
    columnDefinition = "TEXT")
    private String email;
    @Column(name= "address",
    nullable = false,
    columnDefinition = "TEXT")
    private String adress;
    @Column(name = "password",
    nullable = false,
    columnDefinition = "TEXT")
    private String password;
    @Column(name = "username",
    nullable = false,
    columnDefinition = "TEXT")
    private String username;

    @Override
    public String toString(){
        return id+","+ fullName+","+mobile+","+email+","+adress+","+password;
    }

    @Override
    public boolean equals(Object obj){
        Doctor d = (Doctor) obj;

        if(d.fullName.equals(this.fullName)&&d.mobile.equals(this.mobile)&&d.email.equals(this.email)&&d.adress.equals(this.adress)&&d.password.equals(this.password)&&d.username.equals(this.username)){
            return true;
        }
        return false;
     }
}
