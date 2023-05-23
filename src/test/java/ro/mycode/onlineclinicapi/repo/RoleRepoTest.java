package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Role;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
class RoleRepoTest {

    @Autowired
    public RoleRepo roleRepo;

    @BeforeEach
    public void clean(){ roleRepo.deleteAll();}

    @Test
    public void getAllRole(){
        Role role = Role.builder().title("medical doctor").description("test").build();
        Role role1 = Role.builder().title("Junior doctor").description("test1").build();
        Role role2 = Role.builder().title("Consultant").description("test2").build();

        roleRepo.save(role);
        roleRepo.save(role1);
        roleRepo.save(role2);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        roleList.add(role1);
        roleList.add(role2);

        assertEquals(roleList,this.roleRepo.getAllRole());
    }

    @Test
    public void getRoleById(){
        Role role = Role.builder().title("medical doctor").description("test").build();

        roleRepo.save(role);

        assertEquals(role,this.roleRepo.getRoleById(1).get());
    }

    @Test
    public void getRoleByTitle(){
        Role role = Role.builder().title("medical doctor").description("test").build();

        roleRepo.save(role);

        assertEquals(role,this.roleRepo.getRoleByTitle("medical doctor").get());
    }



}