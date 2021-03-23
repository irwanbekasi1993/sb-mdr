package sb.mdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sb.mdr.service.impl.JwtUserDetailsService;
import sb.mdr.service.impl.SessionService;

@RestController
@RequestMapping(value = "sb-mdr/test/v1/token")
public class TokenController {

	@Autowired
	private JwtUserDetailsService tokenService;
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public UserDetails getUserData() throws UsernameNotFoundException {
		UserDetails getUser = tokenService.loadUserByUsername(sessionService.getUserId());
		return getUser;
	}
}
