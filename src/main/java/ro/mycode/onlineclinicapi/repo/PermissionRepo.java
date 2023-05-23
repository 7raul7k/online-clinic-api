package ro.mycode.onlineclinicapi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Permission;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {

    @Query("select p from Permission p where p.title = ?1")
    Optional<Permission> getPermissionByTitle(String title);


    @Query("select p from Permission p where p.role.id = ?1")
    List<Permission> getPermissionByRoleId(long roleId);

    @Query("select p from Permission p")
    List<Permission> getAllPermision();
}
