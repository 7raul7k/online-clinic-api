package ro.mycode.onlineclinicapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "clinics")
@Entity(name = "Clinic")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Clinic {
    @Id
    @SequenceGenerator(name = "clinic_sequence",
    sequenceName = "clinic_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
    generator = "clinic_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name ="type",
    nullable = false,
    columnDefinition = "TEXT")
    private String type;
    @Column(name="description",
    nullable = false,
    columnDefinition = "TEXT")
    private String description;
    @Column(name = "name",
    nullable = false,
    columnDefinition = "TEXT")
    private String  name;
    @Column(name ="place",
    nullable = false,
    columnDefinition = "TEXT")
    private String place;
    @Column(name = "adress",
    nullable = false,
    columnDefinition = "TEXT")
    private String address;


   @OneToMany(mappedBy = "clinic",
   cascade = CascadeType.ALL,
   orphanRemoval = true,
   fetch = FetchType.EAGER)
    @JsonManagedReference
    @Builder.Default
    private List<Doctor> doctorList = new ArrayList<>();

    public void addDoctor(Doctor doctor){
        doctor.setClinic(this);
        doctorList.add(doctor);
    }

    public void removeDoctor(Doctor doctor){
        this.doctorList.remove(doctor);
    }
}
