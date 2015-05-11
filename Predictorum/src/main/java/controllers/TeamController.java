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
import services.TeamService;
import domain.Team;
import forms.TeamToList;

@Controller
@RequestMapping("/team")
public class TeamController extends AbstractController {

	@Autowired
	private TeamService teamService;

	// quitar luego
	@Autowired
	private LoginService loginService;

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public Collection<TeamToList> findAll() {
		authenticate("user1");
		Collection<Team> teams = teamService.findFollowing();
		Collection<TeamToList> teamsToList = teamService
				.reconstructsToList(teams);
		return teamsToList;
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
