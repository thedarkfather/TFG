package controllers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.League;
import domain.Team;

@Controller
@RequestMapping("/league")
public class LeagueController extends AbstractController{
	
	// Listing

		@RequestMapping(method = RequestMethod.GET, value = "/list")
		public Collection<League> findAll() {
			List<League> result = new LinkedList<League>();
			League l1 = new League();
			l1.setId(1);
			l1.setName("league1");
			l1.setSeason("2014/2015");
			l1.setTeams(new LinkedList<Team>());
			l1.setVersion(1);
			result.add(l1);
			return result;
		}

}
