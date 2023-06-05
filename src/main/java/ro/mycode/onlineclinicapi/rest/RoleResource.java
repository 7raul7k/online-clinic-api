package ro.mycode.onlineclinicapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.models.Role;
import ro.mycode.onlineclinicapi.service.RoleService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/role")
public class RoleResource {

    public RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> roleList = this.roleService.getAllRole();
        log.info("REST request to get all role {} ",roleList);

        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PostMapping("/addRole")
    public ResponseEntity<CreateRestResponse> addRole(@RequestBody Role role){

        log.info("REST request to add role {} ",role);

        this.roleService.addRole(role);

        return new ResponseEntity<>(new CreateRestResponse("Role was added!"),HttpStatus.OK);

    }

    @DeleteMapping("/deleteRole/{roleTitle}")
    public ResponseEntity<CreateRestResponse> removeRole(@PathVariable String roleTitle){
        log.info("REST request to remove role by title {} ",roleTitle);

        this.roleService.removeRole(roleTitle);

        return new ResponseEntity<>(new CreateRestResponse("Role was deleted!"),HttpStatus.OK);

    }

    @GetMapping("/getRoleById/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id){
        log.info("REST request to get role by id {} ",id);

        Role role = this.roleService.getRoleById(id);

        return new ResponseEntity<>(role,HttpStatus.OK);

    }

    @GetMapping("/getRoleByTitle/{title}")
    public ResponseEntity<Role> getRoleByTitle(@PathVariable String title){
        log.info("REST request to get role by title {} ",title);

        Role role = this.roleService.getRoleByTitle(title);

        return new ResponseEntity<>(role,HttpStatus.OK);

    }

}
