package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.dto.PermissionDTO;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.exceptions.PermissionNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.PermissionWasFoundException;
import ro.mycode.onlineclinicapi.models.Permission;
import ro.mycode.onlineclinicapi.repo.PermissionRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    public PermissionRepo permissionRepo;

    public PermissionService(PermissionRepo permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    public List<Permission> getAllPermission(){
        List<Permission> permissionList = this.permissionRepo.getAllPermision();

        if(permissionList.isEmpty()){
            throw new ListEmptyException();
        }

        return permissionList;
    }

    public void addPermission(Permission permission){
        Optional<Permission> permissionOptional = this.permissionRepo.getPermissionByTitle(permission.getTitle());

        if(permissionOptional.isEmpty()){
            this.permissionRepo.save(permission);
        }else{
            throw new PermissionWasFoundException();
        }
    }

    public void removePermission(String permissionTitle){
        Optional<Permission> permissionOptional = this.permissionRepo.getPermissionByTitle(permissionTitle);

        if(permissionOptional.isEmpty()){
            throw new PermissionNotFoundException();
        }else{
            this.permissionRepo.delete(permissionOptional.get());
        }
    }

    public Permission getPermissionByTitle(String title){
        Optional<Permission> permissionOptional = this.permissionRepo.getPermissionByTitle(title);

        if(permissionOptional.isEmpty()){
            throw new PermissionNotFoundException();
        }

        return permissionOptional.get();
    }

    public Permission getPermissionbyModule(String module){
        Optional<Permission> permissionOptional = this.permissionRepo.getPermissionByModule(module);

        if(permissionOptional.isEmpty()){
            throw new PermissionNotFoundException();
        }

        return permissionOptional.get();
    }

    public void updatePermission(PermissionDTO permissionDTO){
        Optional<Permission> updatePermission = this.permissionRepo.getPermissionByTitle(permissionDTO.getTitle());


        if(updatePermission.isEmpty()){
            throw new PermissionNotFoundException();
        }

        if(permissionDTO.getModule() != null){
            updatePermission.get().setModule(permissionDTO.getModule());
        }else if(permissionDTO.getDescription() != null){
            updatePermission.get().setDescription(permissionDTO.getDescription());
        }

        permissionRepo.saveAndFlush(updatePermission.get());
    }
}
