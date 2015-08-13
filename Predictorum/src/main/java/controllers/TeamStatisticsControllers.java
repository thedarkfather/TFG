package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.TeamStatistics;
import forms.TeamStatisticsForm;
import services.TeamStatisticsService;

@Controller
@RequestMapping("/teamStatistics")
public class TeamStatisticsControllers extends AbstractController{
	
	@Autowired
	private TeamStatisticsService teamStatisticsService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{teamId}")
	public TeamStatisticsForm findByTeamId(@PathVariable Integer teamId) {
		TeamStatistics teamStatistics = teamStatisticsService.findByTeamId(teamId);
		TeamStatisticsForm teamStatisticsForm = teamStatisticsService.reconstruct(teamStatistics);
		return teamStatisticsForm;
	}

}
