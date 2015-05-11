package services;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.League;
import forms.LeagueToList;
import repositories.LeagueRepository;

@Service
@Transactional
public class LeagueService {
	
	@Autowired
	private LeagueRepository leagueRepository;
	
	public Collection<League> findAll(){
		Collection<League> leagues = leagueRepository.findAll();
		return leagues;
	}
	
	public LeagueToList reconstructToList(League league){
		LeagueToList leagueToList = new LeagueToList();
		leagueToList.setId(league.getId());
		leagueToList.setName(league.getName());
		leagueToList.setSeason(league.getSeason());
		return leagueToList;
	}
	
	public Collection<LeagueToList> reconstructsToList(Collection<League> leagues){
		Collection<LeagueToList> leaguesToList = new LinkedList<LeagueToList>();
		for(League league : leagues){
			LeagueToList leagueToList = reconstructToList(league);
			leaguesToList.add(leagueToList);
		}
		return leaguesToList;
	}

}
