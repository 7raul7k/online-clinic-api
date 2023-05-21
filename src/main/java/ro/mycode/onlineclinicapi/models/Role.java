package ro.mycode.onlineclinicapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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

}

