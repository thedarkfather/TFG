package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import security.LoginService;
import services.UserService;
import domain.User;
import forms.UserToList;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	@Autowired
	private UserService userService;

	// quitar luego
	@Autowired
	private LoginService loginService;

	@RequestMapping(method = RequestMethod.GET, value = "/listFollowers")
	public Collection<UserToList> findFollowers() {
		authenticate("user4");
		Collection<User> userAux = userService.findFollowers();
		Collection<UserToList> usersToList = userService.reconstructsToList(userAux);
		return usersToList;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listFollowing")
	public Collection<UserToList> findFollowing() {
		authenticate("user3");
		Collection<User> userAux = userService.findFollowing();
		Collection<UserToList> usersToList = userService.reconstructsToList(userAux);
		return usersToList;
	}

	// quitar luego
	public void authenticate(String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		userDetails = loginService.loadUserByUsername(username);
		authenticationToken = new TestingAuthenticationToken(userDetails, null);
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

}
