package sbmdr.controller.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.Mahasiswa;
import sbmdr.model.RefreshToken;
import sbmdr.model.Role;
import sbmdr.model.User;
import sbmdr.model.redis.RedisUser;
import sbmdr.payload.request.LoginRequest;
import sbmdr.payload.request.ResetPasswordRequest;
import sbmdr.payload.request.SignupRequest;
import sbmdr.payload.request.TokenRefreshRequest;
import sbmdr.payload.response.JwtResponse;
import sbmdr.payload.response.MessageResponse;
import sbmdr.payload.response.TokenRefreshResponse;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MahasiswaRepository;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;
import sbmdr.repository.redis.RedisUserRepository;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.MahasiswaService;
import sbmdr.service.impl.RefreshTokenService;
import sbmdr.service.impl.SignupService;
import sbmdr.service.impl.UserDetailsImpl;
import sbmdr.util.JwtTokenUtil;



@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/sbmdr/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private SignupService signupService; 


    @Autowired
    private RedisUserRepository redisUserRepository;

private KafkaProducerConfig kafkaProducerAuth;
private KafkaConsumerConfig kafkaConsumerAuth;

private void kafkaProcessing(String result){
    kafkaProducerAuth.sendMessage("kafka producer jadwal produce result: "+result);
    kafkaConsumerAuth.consumeMessage("kafka consumer jadwal consume result: "+result);
}

 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        JwtResponse response = new JwtResponse(jwt,refreshToken.getToken(),userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles);
        
        TokenRefreshRequest requestToken = new TokenRefreshRequest();
        requestToken.setRefreshToken(response.getToken());

        refreshTokenService.findByToken(requestToken.getRefreshToken()).map(refreshTokenService::verifyExpiration);
        User getUser = refreshToken.getUser();
        if(getUser!=null){
            kafkaProcessing("signin using username: "+getUser.getUsername());
        }
        String obtainToken = jwtUtils.generateTokenFromUsername(getUser.getUsername());
        TokenRefreshResponse tokenResponse = new TokenRefreshResponse(obtainToken,requestToken.getRefreshToken(),getUser.getUsername(),getUser.getRoles());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            kafkaProcessing("username is exists");
            return ResponseEntity.badRequest().body(new MessageResponse("username is exists"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            kafkaProcessing("email is exists");
            return ResponseEntity.badRequest().body(new MessageResponse("email is exists"));
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles==null){
            Role userRole = roleRepository.findByName(user.getRoles().stream().map(x->x.getName()).toString()).orElseThrow(()-> new RuntimeException("role not found"));
            roles.add(userRole);
        }
        // else{
            // strRoles.forEach(role->{
            //     switch(role){
            //         case "admin": 
            //         Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(adminRole);
            //         break;
            //         case "mod": 
            //         Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(modRole);
            //         break;
            //         default: 
            //         Role userRole = roleRepository.findByName(ERole.ROLE_USER.name()).orElseThrow(()-> new RuntimeException("role not found"));
            //         roles.add(userRole);
                    
            //     }
            // });
        // }
        for(String strRole: strRoles){
            switch(strRole){
                case "ROLE_DOSEN":
                Role roleDosen = roleRepository.findByName("ROLE_DOSEN").get();
                    Dosen dosen = new Dosen();
                    dosen.setNip("DSN"+signupRequest.getUsername());
                    dosen.setNamaDosen(signupRequest.getUsername());
                    dosen.setEmail(signupRequest.getEmail());
                    dosen.setStatusDosen("AKTIF");
                    signupService.insertDosen(dosen);
                    roles.add(roleDosen);
                    break;
                

                case "ROLE_MAHASISWA":
                Role roleMahasiswa = roleRepository.findByName("ROLE_MAHASISWA").get();
                    Mahasiswa mahasiswa = new Mahasiswa();
                    mahasiswa.setNim("MHS"+signupRequest.getUsername());
                mahasiswa.setNamaMahasiswa(signupRequest.getUsername());
                mahasiswa.setEmail(signupRequest.getEmail());
                mahasiswa.setStatusMahasiswa("AKTIF");
                signupService.insertMahasiswa(mahasiswa);
                roles.add(roleMahasiswa);
                break;
                
                default:
                Role role = roleRepository.findByName(strRole).get();
            roles.add(role);
            }
            
        }
        user.setRoles(roles);
        userRepository.save(user);
        RedisUser redisUser = new RedisUser();
        redisUser.setUsername(user.getUsername());
        redisUser.setEmail(user.getEmail());
        redisUserRepository.save(redisUser);
        kafkaProcessing("user registered successfully");
        return ResponseEntity.ok(new MessageResponse("user registered successfully"));
    }


        @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPassword){
        boolean flag = userRepository.existsByUsername(resetPassword.getUsername());
        if(!flag){
            return ResponseEntity.ok(new MessageResponse("User not exists"));
        }
            userRepository.resetPassword(encoder.encode(resetPassword.getNewPassword()), resetPassword.getUsername());
            return ResponseEntity.ok(new MessageResponse("Password Changed Successfully"));
        
    }

}
