package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Season;
import repositories.SeasonRepository;

@Service
public class SeasonService {
	
	@Autowired
	private SeasonRepository seasonRepository;
	
	public Season saveEasy(Season season){
		Assert.notNull(season);
		return seasonRepository.save(season);
	}

	public Season findByLeagueId(Integer leagueId) {
		Assert.notNull(leagueId);
		Season season = seasonRepository.findByLeagueId(leagueId);
		return season;
	}

}
