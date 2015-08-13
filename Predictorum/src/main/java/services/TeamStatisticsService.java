package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Result;
import domain.Season;
import domain.Team;
import domain.TeamStatistics;
import forms.TeamStatisticsForm;
import repositories.TeamStatisticsRepository;

@Service
public class TeamStatisticsService {
	
	@Autowired
	private TeamStatisticsRepository teamStatisticsRepository;
	
	@Autowired
	private SeasonService seasonService;
	
	@Autowired
	private TeamService teamService;
	
	public void Save(TeamStatistics teamStatistics){
		Assert.notNull(teamStatistics);
		teamStatisticsRepository.save(teamStatistics);
	}

	public void update(Result result) {
		Assert.notNull(result);
		TeamStatistics tSTeam1 = teamStatisticsRepository.findByTeamId(result.getGame().getHomeTeam().getId());
		TeamStatistics tSTeam2 = teamStatisticsRepository.findByTeamId(result.getGame().getAwayTeam().getId());
		tSTeam1.setTotalGames(tSTeam1.getTotalGames()+1);
		tSTeam2.setTotalGames(tSTeam2.getTotalGames()+1);
		tSTeam1.setHomeGoals(tSTeam1.getHomeGoals()+result.getHomeGoals());
		tSTeam2.setAwayGoals(tSTeam2.getAwayGoals()+result.getAwayGoals());
		if(result.getHomeGoals().compareTo(result.getAwayGoals())>0){
			//Ha gando local
			tSTeam1.setHomeWonMatches(tSTeam1.getHomeWonMatches()+1);
			tSTeam2.setAwayLostMatches(tSTeam2.getAwayLostMatches()+1);
			tSTeam1.setPoints(tSTeam1.getPoints()+3);
			tSTeam1.setStreak(tSTeam1.getStreak()+"V");
			tSTeam2.setStreak(tSTeam2.getStreak()+"D");
		}else if(result.getHomeGoals().compareTo(result.getAwayGoals())<0){
			//Ha ganado visitante
			tSTeam2.setAwayWonMatches(tSTeam2.getAwayWonMatches()+1);
			tSTeam1.setHomeLostMatches(tSTeam1.getHomeLostMatches()+1);
			tSTeam2.setPoints(tSTeam2.getPoints()+3);
			tSTeam2.setStreak(tSTeam2.getStreak()+"V");
			tSTeam1.setStreak(tSTeam1.getStreak()+"D");
		}else{
			//Han quedado empates
			tSTeam1.setPoints(tSTeam1.getPoints()+1);
			tSTeam2.setPoints(tSTeam2.getPoints()+1);
			tSTeam1.setStreak(tSTeam1.getStreak()+"X");
			tSTeam2.setStreak(tSTeam2.getStreak()+"X");
		}
		tSTeam1.setWonMatchPercentage(new Integer((int) ((100.0*(tSTeam1.getHomeWonMatches()+tSTeam1.getAwayWonMatches()))/tSTeam1.getTotalGames())));
		tSTeam2.setWonMatchPercentage(new Integer((int) ((100.0*(tSTeam2.getHomeWonMatches()+tSTeam2.getAwayWonMatches()))/tSTeam2.getTotalGames())));
		tSTeam1.setLostMatchPercentage(new Integer((int) ((100.0*(tSTeam1.getHomeLostMatches()+tSTeam1.getAwayLostMatches()))/tSTeam1.getTotalGames())));
		tSTeam2.setLostMatchPercentage(new Integer((int) (100.0*(tSTeam2.getHomeLostMatches()+tSTeam2.getAwayLostMatches())/tSTeam2.getTotalGames())));
		tSTeam1.setDrawMatchPercentage(100-tSTeam1.getWonMatchPercentage()-tSTeam1.getLostMatchPercentage());
		tSTeam2.setDrawMatchPercentage(100-tSTeam2.getWonMatchPercentage()-tSTeam2.getLostMatchPercentage());
		if(result.getHomeGoals()>2){
			tSTeam1.setMoreThan25(tSTeam1.getMoreThan25()+1);
		}
		if(result.getAwayGoals()>2){
			tSTeam2.setMoreThan25(tSTeam2.getMoreThan25()+1);
		}
		tSTeam1.setMoreThan25Percentage(new Integer( (int) (( tSTeam1.getMoreThan25()*1.0)/tSTeam1.getTotalGames()) ));
		tSTeam2.setMoreThan25Percentage(new Integer( (int) (( tSTeam2.getMoreThan25()*1.0)/tSTeam2.getTotalGames()) ));
		teamStatisticsRepository.save(tSTeam1);
		teamStatisticsRepository.save(tSTeam2);	
	}
	
	public void updateLeaguePosition(Season season) {
		Assert.notNull(season);
		List<TeamStatistics> TeamStatisticses = teamStatisticsRepository.findOrderByPoints(season.getId());
		int tam = TeamStatisticses.size();
		for (int i = 0; i < tam; i++) {
			TeamStatistics teamStatistics = TeamStatisticses.get(i);
			teamStatistics.setLeaguePosition(i + 1);
			teamStatisticsRepository.save(teamStatistics);
		}
	}

	public void createAndSave(Team team) {
		Assert.notNull(team);
		TeamStatistics teamStatistics = new TeamStatistics();
		teamStatistics.setStreak("");
		teamStatistics.setWonMatchPercentage(0);
		teamStatistics.setLostMatchPercentage(0);
		teamStatistics.setDrawMatchPercentage(0);
		teamStatistics.setMoreThan25Percentage(0);
		teamStatistics.setMoreThan25(0); 
		teamStatistics.setHomeWonMatches(0); 
		teamStatistics.setAwayWonMatches(0); 
		teamStatistics.setHomeLostMatches(0); 
		teamStatistics.setAwayLostMatches(0); 
		teamStatistics.setHomeGoals(0); 
		teamStatistics.setAwayGoals(0); 
		teamStatistics.setLeaguePosition(0);
		teamStatistics.setPoints(0); 
		teamStatistics.setTotalGames(0); 
		teamStatistics.setTeam(team);
		teamStatisticsRepository.save(teamStatistics);
	}

	public TeamStatistics findByTeamId(Integer teamId) {
		Assert.notNull(teamId);
		TeamStatistics teamStatistics = teamStatisticsRepository.findByTeamId(teamId);
		return teamStatistics;
	}

	public TeamStatisticsForm reconstruct(TeamStatistics teamStatistics) {
		Assert.notNull(teamStatistics);
		TeamStatisticsForm teamStatisticsForm = new TeamStatisticsForm();
		teamStatisticsForm.setStreak(teamStatistics.getStreak());
		teamStatisticsForm.setWonMatchPercentage(teamStatistics.getWonMatchPercentage());
		teamStatisticsForm.setLostMatchPercentage(teamStatistics.getLostMatchPercentage());
		teamStatisticsForm.setDrawMatchPercentage(teamStatistics.getDrawMatchPercentage());
		teamStatisticsForm.setMoreThan25Percentage(teamStatistics.getMoreThan25Percentage());
		teamStatisticsForm.setMoreThan25(teamStatistics.getMoreThan25()); 
		teamStatisticsForm.setHomeWonMatches(teamStatistics.getHomeWonMatches());
		teamStatisticsForm.setAwayWonMatches(teamStatistics.getAwayWonMatches()); 
		teamStatisticsForm.setHomeLostMatches(teamStatistics.getHomeLostMatches()); 
		teamStatisticsForm.setAwayLostMatches(teamStatistics.getAwayLostMatches()); 
		teamStatisticsForm.setHomeGoals(teamStatistics.getHomeGoals()); 
		teamStatisticsForm.setAwayGoals(teamStatistics.getAwayGoals()); 
		teamStatisticsForm.setLeaguePosition(teamStatistics.getLeaguePosition());
		teamStatisticsForm.setPoints(teamStatistics.getPoints()); 
		teamStatisticsForm.setTotalGames(teamStatistics.getTotalGames()); 
		teamStatisticsForm.setTeamName(teamStatistics.getTeam().getName());
		teamStatisticsForm.setLeagueName(teamStatistics.getTeam().getSeason().getLeague().getName());		
		return teamStatisticsForm;
	}

}
