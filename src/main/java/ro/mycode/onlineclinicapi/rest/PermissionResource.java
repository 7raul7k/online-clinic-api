package ro.mycode.onlineclinicapi.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.PermissionDTO;
import ro.mycode.onlineclinicapi.models.Permission;
import ro.mycode.onlineclinicapi.service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permission")
@Slf4j
public class PermissionResource {

    public PermissionService permissionService;

    public PermissionResource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/getAllPermission")
    public ResponseEntity<List<Permission>> getAllPermission(){
        List<Permission> permissionList = this.permissionService.getAllPermission();

        log.info("REST request to get all permission {}",permissionList);


        return new ResponseEntity<>(permissionList, HttpStatus.OK);
    }

    @PostMapping("/addPermission")
    public ResponseEntity<CreateRestResponse> addPermission(@RequestBody PermissionDTO permissionDTO){

        this.permissionService.addPermission(permissionDTO);

        log.info("REST request to add permission {}",permissionDTO);

        return new ResponseEntity<>(new CreateRestResponse("Permission was added!"),HttpStatus.OK);

    }

    @DeleteMapping("/removePermission")
    public ResponseEntity<CreateRestResponse> removePermission(@RequestParam String name){
        this.permissionService.removePermission(name);

        log.info("REST request to remove permission by name {}",name);

        return new ResponseEntity<>(new CreateRestResponse("Permission was deleted!"),HttpStatus.OK);

    }

    @GetMapping("/getPermissionByTitle")
    public ResponseEntity<Permission> getPermissionByTitle(@RequestParam String title){
        Permission permission = this.permissionService.getPermissionByTitle(title);

        log.info("REST request to get permission by title {}",title);

        return new ResponseEntity<>(permission,HttpStatus.OK);
    }

    @GetMapping("/getPermissionByModule")
    public ResponseEntity<Permission> getPermissionByModule(@RequestParam String module){
        Permission permission = this.permissionService.getPermissionbyModule(module);

        log.info("REST request to get permission by module {}",module);

        return new ResponseEntity<>(permission,HttpStatus.OK);
    }

    @PutMapping("/updatePermission")
    public ResponseEntity<CreateRestResponse> updatePermission(@RequestBody PermissionDTO permissionDTO){

        this.permissionService.updatePermission(permissionDTO);

        log.info("REST request to update permission {}", permissionDTO);

        return new ResponseEntity<>(new CreateRestResponse("Permission was updated!"),HttpStatus.OK);
    }
 }
