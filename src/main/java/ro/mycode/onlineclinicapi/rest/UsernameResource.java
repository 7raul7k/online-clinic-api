package ro.mycode.onlineclinicapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineclinicapi.dto.CreateRestResponse;
import ro.mycode.onlineclinicapi.dto.UsernameDTO;
import ro.mycode.onlineclinicapi.models.Username;
import ro.mycode.onlineclinicapi.service.UsernameService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/username")
@Slf4j
public class UsernameResource {

    public UsernameService usernameService;

    public UsernameResource(UsernameService usernameService) {
        this.usernameService = usernameService;
    }

    @GetMapping("/getAllUsername")
    public ResponseEntity<List<Username>> getAllUsername(){

        List<Username> usernameList = this.usernameService.getAllUsername();

        log.info("REST request to get all usernames {}", usernameList);

        return new ResponseEntity<>(usernameList, HttpStatus.OK);
    }

    @PostMapping("/addUsername")
    public ResponseEntity<CreateRestResponse> addUsername(@RequestBody UsernameDTO usernameDTO){
        this.usernameService.addUsername(usernameDTO);

        log.info("REST request to add username {}", usernameDTO);

        return new ResponseEntity<>(new CreateRestResponse("Username was added!"),HttpStatus.OK);

    }

    @DeleteMapping("/deleteUsername")
    public ResponseEntity<CreateRestResponse> deleteUsername(@RequestParam String name){
        this.usernameService.deleteUsername(name);

        log.info("REST request to delete  username by name {}", name);

        return new ResponseEntity<>(new CreateRestResponse("Username was deleted"),HttpStatus.OK);
    }

    @GetMapping("/getUsernameByName")
    public ResponseEntity<Username> getUsernameByName(@RequestParam String name){
        Username username = this.usernameService.getUsernameByName(name);

        log.info("REST request to get username by name {}", name);

        return new ResponseEntity<>(username,HttpStatus.OK);
    }

    @GetMapping("/getUsernameByEmail/{email}")
    public ResponseEntity<Username> getUsernameByEmail(@PathVariable String email){
        Username username = this.usernameService.getUsernameByEmail(email);

        log.info("REST request to get username by email {}", email);

        return new ResponseEntity<>(username,HttpStatus.OK);
    }

    @PutMapping("/updateUsername")
    public ResponseEntity<CreateRestResponse> updateUsername(@RequestBody UsernameDTO usernameDTO){
        this.usernameService.updateUsername(usernameDTO);

        log.info("REST request to update username {}", usernameDTO);

        return new ResponseEntity<>(new CreateRestResponse("Username was updated"),HttpStatus.OK);
    }
}
