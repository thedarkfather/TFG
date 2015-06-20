package services;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
	
	public Collection<Team> findAll(){
		Collection<Team> teams = teamRepository.findAll();
		return teams;
	}
	
	public TeamToList reconstructToList(Team team){
		TeamToList teamToList = new TeamToList();
		teamToList.setFollowing(isFollowed(team));
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

	public boolean isFollowed(Team team){
		User principal = userService.findByPrincipal();
		Team aux = teamRepository.findIsFollowed(team.getId(),principal.getId());
		boolean res = aux!=null;
		return res;
	}
	
	public Team findByTeamName(String teamName){
		Assert.notNull(teamName);
		Team team = teamRepository.findByTeamName(teamName);
		return team;
	}
	
	public Team saveEasy(Team team){
		Assert.notNull(team);
		return teamRepository.save(team);
	}

}
