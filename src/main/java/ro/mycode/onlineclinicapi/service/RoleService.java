package ro.mycode.onlineclinicapi.service;

import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.RoleNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.RoleWasFoundException;
import ro.mycode.onlineclinicapi.models.Role;
import ro.mycode.onlineclinicapi.repo.RoleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    public RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> getAllRole(){
        List<Role> roleList = this.roleRepo.getAllRole();

        if(roleList.isEmpty()){
            throw new ListEmptyException();
        }

        return roleList;
    }

    public void addRole(Role role){
        Optional<Role> roleOptional = this.roleRepo.getRoleByTitle(role.getTitle());

        if(roleOptional.isEmpty()){
            roleRepo.save(role);
        }else{
            throw new RoleWasFoundException();
        }
    }

    public void removeRole(String roleTitle){
        Optional<Role> roleOptional = this.roleRepo.getRoleByTitle(roleTitle);

        if(roleOptional.isEmpty()){
            throw new RoleNotFoundException();
        }else{
            this.roleRepo.delete(roleOptional.get());
        }
    }

    public Role getRoleById(int id){
        Optional<Role> roleOptional = this.roleRepo.getRoleById((long) id);

        if(roleOptional.isEmpty()){
            throw new RoleNotFoundException();
        }

        return roleOptional.get();
    }

    public Role getRoleByTitle(String title){
        Optional<Role> roleOptional = this.roleRepo.getRoleByTitle(title);

        if(roleOptional.isEmpty()){
            throw new RoleNotFoundException();
        }

        return roleOptional.get();
    }
}
