package services;


import java.util.Calendar;
import java.util.Date;

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

	public Season findCurrentByLeagueId(Integer leagueId) {
		Assert.notNull(leagueId);
		Date current = new Date(System.currentTimeMillis());	
		Calendar finishCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		int realMonth = calendar.get(Calendar.MONTH) + 1;
		int realYear = calendar.get(Calendar.YEAR);
		if (realMonth > 7) {	
			finishCalendar.set(realYear + 1, 5, 30);
		} else {
			finishCalendar.set(realYear, 5, 30);
		}
		Date finishDate = finishCalendar.getTime();
		Season season = seasonRepository.findCurrentByLeagueId(leagueId,finishDate);
		return season;
	}

}
