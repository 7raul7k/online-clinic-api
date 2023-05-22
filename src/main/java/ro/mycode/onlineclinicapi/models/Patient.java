package ro.mycode.onlineclinicapi.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name ="patients")
@Entity(name ="Patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Patient {
    @Id
    @SequenceGenerator(name = "patient_sequence",
    sequenceName = "patient_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy =  SEQUENCE,
    generator = "patient_sequence")
    @Column(name ="id")
    private Long id;
    @Column(name = "full_name",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String fullName;
    @Column(name = "mobile",
    nullable = false,
    columnDefinition = "TEXT")
    private String number;
    @Column(name = "address",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String adress;
    @Column(name ="email",
    nullable = false,
    columnDefinition = "TEXT")
    private String email;
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
        return id+","+fullName+","+number+","+adress+","+email+","+password+","+username;
    }

    @Override
    public boolean equals(Object obj){
    Patient p = (Patient) obj;
    if(p.fullName.equals(this.fullName)&&p.number.equals(this.number)&&p.adress.equals(this.adress)&&p.email.equals(this.email)&&p.password.equals(this.password)&&p.username.equals(this.username)){
        return true;
    }
    return false;
    }

    @OneToMany(mappedBy = "patient",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Test> testList = new ArrayList<>();

    public void addTest(Test test){
        test.setPatient(this);
        testList.add(test);
    }

    public void removeTest(Test test){
        testList.remove(test);

    }
}
