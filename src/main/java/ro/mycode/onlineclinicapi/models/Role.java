package ro.mycode.onlineclinicapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "roles")
@Entity(name = "Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Role {
    @Id
    @SequenceGenerator(name ="roles_sequence",
    sequenceName = "roles_sequence",
    allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "order_sequence"
    )
    @Column(name ="id")
    private Long id;
    @Column(name ="title",
    nullable = false,
    columnDefinition = "TEXT")
    private String title;
    @Column(name ="description",
    nullable = false,
    columnDefinition = "TEXT")
    private String description;

    @Override
    public String toString(){
        return id+","+title+","+description;
    }

    @Override
    public boolean equals(Object object){
        Role r = (Role) object;
        if(r.title.equals(this.title)&&r.description.equals(this.description)){
            return true;
        }
        return false;

    }

    @OneToMany(mappedBy = "role",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER)
    @JsonManagedReference(value = "test4")
    private List<Username> usernameList = new ArrayList<>();

    public void addUsername(Username username){
        username.setRole(this);
        this.usernameList.add(username);
    }
    public void removeUsername(Username username){
        this.usernameList.remove(username);
    }







}

