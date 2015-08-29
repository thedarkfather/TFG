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
import services.TeamService;
import domain.Team;
import forms.FollowTeamForm;
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
		Collection<Team> teams = teamService.findCurrentTeams();
		Collection<TeamToList> teamsToList = teamService.reconstructsToList(teams);
		return teamsToList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse follow(@RequestBody @Valid FollowTeamForm followTeamForm, BindingResult binding){
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(followTeamForm, binding));
		} else {
			try{
				teamService.followTeam(followTeamForm.getTeamId());
				generalResponse= new GeneralResponse(true, new HashMap<String,String>());
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}

}
