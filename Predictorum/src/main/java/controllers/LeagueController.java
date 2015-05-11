package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.LeagueService;
import domain.League;
import forms.LeagueToList;

@Controller
@RequestMapping("/league")
public class LeagueController extends AbstractController {
	
	@Autowired
	private LeagueService leagueService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public Collection<LeagueToList> findAll() {
		Collection<League> leagues = leagueService.findAll();
		Collection<LeagueToList> leaguesToList = leagueService.reconstructsToList(leagues);
		return leaguesToList;
	}

}
