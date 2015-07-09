package services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoundRepository;
import domain.Round;

@Service
public class RoundService {
	
	@Autowired
	private RoundRepository roundRepository;
	
	public Round saveEasy(Round round){
		Assert.notNull(round);
		return roundRepository.save(round);
	}

	public Round findNoUpdatedBySeasonIdAndRoundNumber(Integer seasonId,Integer roundNumber) {
		Assert.notNull(seasonId);
		Round round = roundRepository.findNoUpdatedBySeasonIdAndRoundNumber(seasonId,roundNumber);
		return round;
	}
	
	public Round nextRound(Integer leagueId){
		List<Round> rounds = roundRepository.nextRounds(leagueId);
		Round round = null;
		if(!rounds.isEmpty()){
			round = rounds.get(0);
		}
		return round;
	}

}
