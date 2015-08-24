package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Diary;
import domain.Result;
import domain.TeamStatistics;
import repositories.DiaryRepository;

@Service
public class DiaryService {

	@Autowired
	private DiaryRepository diaryRepository;
	
	public void create(Result result){
		Assert.notNull(result);
		TeamStatistics local = result.getGame().getHomeTeam().getTeamStatistics();
		TeamStatistics away = result.getGame().getAwayTeam().getTeamStatistics();
		Diary diary = new Diary();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(result.getGame().getRound().getStartDate());
		diary.setStartYearSeason(calendar.get(Calendar.YEAR));
		diary.setTeam1(result.getGame().getHomeTeam().getName());
		diary.setHomeWonMatches1(local.getHomeWonMatches());
		diary.setAwayWonMatches1(local.getAwayWonMatches());
		diary.setHomeLostMatches1(local.getHomeLostMatches());
		diary.setAwayLostMatches1(local.getAwayLostMatches());
		diary.setPoints1(local.getPoints());
		diary.setTotalGames1(local.getTotalGames());
		diary.setTeam2(result.getGame().getAwayTeam().getName());
		diary.setHomeWonMatches2(away.getHomeWonMatches());
		diary.setAwayWonMatches2(away.getAwayWonMatches());
		diary.setHomeLostMatches2(away.getHomeLostMatches());
		diary.setAwayLostMatches2(away.getAwayLostMatches());
		diary.setPoints2(away.getPoints());
		diary.setTotalGames2(away.getTotalGames());
		diary.setTotalAwayGoals1(local.getMoreThan25());
		diary.setTotalAwayGoals2(away.getMoreThan25());
		diary.setTotalHomeGoals1(local.getHomeGoals());
		diary.setTotalHomeGoals2(away.getHomeGoals());
		diary.setTotalMoreThan251(local.getMoreThan25());
		diary.setTotalMoreThan252(away.getMoreThan25());
		diary.setHomeGoals(result.getHomeGoals());
		diary.setAwayGoals(result.getAwayGoals());
		Integer totalGoals = result.getHomeGoals() + result.getAwayGoals();
		boolean mThan25 = totalGoals.compareTo(3)>=0;
		String smThan25;
		if(mThan25){
			smThan25 = "true";
		}else{
			smThan25 = "false";
		}
		diary.setMoreThan25(smThan25);
		String winner = "X";
		if(result.getHomeGoals().compareTo(result.getAwayGoals())>0){
			winner = "1";
		}else if(result.getHomeGoals().compareTo(result.getAwayGoals())<0){
			winner = "2";
		}
		diary.setWinner(winner);
		diaryRepository.save(diary);
	}
	
	public Collection<Diary> findAll(){
		Collection<Diary> diaries = diaryRepository.findAll();
		return diaries;
	}
}
