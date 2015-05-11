package services;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Team;
import domain.User;
import forms.TeamToList;
import repositories.TeamRepository;

@Service
@Transactional
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserService userService;

	public Collection<Team> findFollowing() {
		User principal = userService.findByPrincipal();
		Collection<Team> teams = teamRepository.findByPrincipal(principal.getId());
		return teams;
	}
	
	public TeamToList reconstructToList(Team team){
		TeamToList teamToList = new TeamToList();
		teamToList.setFollowing(true);//hay que cambiarlo si vamos a listar todos los equipos de otra forma
		teamToList.setLeagueId(team.getLeague().getId());
		teamToList.setLeagueName(team.getLeague().getName());
		teamToList.setPosLeague(team.getTeamStatistics().getLeaguePosition());
		teamToList.setTeamId(team.getId());
		teamToList.setTeamName(team.getName());
		return teamToList;
	}
	
	public Collection<TeamToList> reconstructsToList(Collection<Team> teams){
		Collection<TeamToList> teamsToList = new LinkedList<TeamToList>();
		for(Team team : teams){
			TeamToList teamToList = reconstructToList(team);
			teamsToList.add(teamToList);
		}
		return teamsToList;
	}

}
