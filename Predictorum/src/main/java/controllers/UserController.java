package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import responses.GeneralResponse;
import security.LoginService;
import services.UserService;
import domain.User;
import forms.FollowTeamForm;
import forms.FollowUserForm;
import forms.UserToList;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	@Autowired
	private UserService userService;

	// quitar luego
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public Collection<UserToList> findAll() {
		authenticate("user3");
		Collection<User> userAux = userService.findAll();
		Collection<UserToList> usersToList = userService.reconstructsToList(userAux);
		return usersToList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listFollowers")
	public Collection<UserToList> findFollowers() {
		authenticate("user4");
		Collection<User> userAux = userService.findFollowers();
		Collection<UserToList> usersToList = userService.reconstructsToList(userAux);
		return usersToList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse follow(@RequestBody @Valid FollowUserForm followUserForm, BindingResult binding){
		authenticate("user1");
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(followUserForm, binding));
		} else {
			try{
				userService.followUser(followUserForm.getUserId());
				generalResponse= new GeneralResponse(true, new HashMap<String,String>());
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
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
