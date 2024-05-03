package sbmdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.model.Role;
import sbmdr.payload.response.MessageResponse;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/sbmdr/test/v1")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;


@PostMapping("/insertRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertRole(@RequestBody Role role){
        boolean flag = roleRepository.findByName(role.getName()).isPresent();
        if(!flag){
            Role cekRole = roleRepository.save(role);
            return ResponseEntity.ok(cekRole); 
        }
        return ResponseEntity.ok(new MessageResponse("Role Already Exists")); 
        
    }

    @PutMapping("/updateRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@RequestBody Role role,@PathVariable("name") String name){
        boolean flag = roleRepository.findByName(role.getName()).isPresent();
        if(flag){
            Role cekRole = roleRepository.save(role);
            return ResponseEntity.ok(cekRole); 
        }
        return ResponseEntity.ok(new MessageResponse("Role Not Found")); 
     
    }

    @DeleteMapping("/deleteRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable("name") String name){
        Role cekRole = roleRepository.findByName(name).get();
        if(cekRole!=null){
            roleRepository.delete(cekRole);
        }
        return ResponseEntity.ok(new MessageResponse("role successfully deleted"));
    }

}
