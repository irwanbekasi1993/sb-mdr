package sb.mdr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sb.mdr.util.JwtTokenUtil;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Value("${jwt.password}")
	private String password;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = new User(username,sessionService.encryptPassword(password),new ArrayList<>());
		authenticate(username,sessionService.encryptPassword(password));
		String token = jwtTokenUtil.generateToken(user);
		System.err.println(token);
		return user;
	}
	
	public void authenticate(String username, String password) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
}
