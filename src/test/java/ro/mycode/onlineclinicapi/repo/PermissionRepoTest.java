package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Permission;
import ro.mycode.onlineclinicapi.models.Role;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
@Transactional
class PermissionRepoTest {

    @Autowired
    public PermissionRepo permissionRepo;

    @BeforeEach
    public void clean(){permissionRepo.deleteAll();}

    @Test
    public void getAllPermission(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();
        Permission permission1 = Permission.builder().title("test1").description("test1").module("test1").build();
        Permission permission2 = Permission.builder().title("test2").description("test2").module("test2").build();

        permissionRepo.save(permission);
        permissionRepo.save(permission1);
        permissionRepo.save(permission2);

        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(permission);
        permissionList.add(permission1);
        permissionList.add(permission2);

        assertEquals(permissionList,this.permissionRepo.getAllPermision());
    }

    @Test
    public void getPermissionByModule(){

        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        permissionRepo.save(permission);


        assertEquals(permission,this.permissionRepo.getPermissionByModule("test").get());
    }

    @Test
    public void getPermissionByTitle(){
        Permission permission = Permission.builder().title("test").description("test").module("test").build();

        permissionRepo.save(permission);

        assertEquals(permission,this.permissionRepo.getPermissionByTitle("test").get());
    }
}