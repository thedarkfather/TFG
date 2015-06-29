package services;

import java.util.Collection;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.League;
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

}
