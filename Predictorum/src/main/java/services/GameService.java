package services;


import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GameRepository;
import domain.Game;
import domain.League;
import domain.Round;
import forms.GameToListForm;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private SeasonService seasonService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private RoundService roundService;
	
	public Game saveEasy(Game game){
		Assert.notNull(game);
		return gameRepository.save(game);
		
	}

	public Game findByRoundIdAndLocalTeamAndAwayTeam(Integer roundId,String localTeam, String awayTeam) {
		Assert.notNull(roundId);
		Assert.notNull(localTeam);
		Assert.notNull(awayTeam);
		Game game = gameRepository.findByRoundIdAndLocalTeamAndAwayTeam(roundId,localTeam,awayTeam);
		return game;		
	}
	
	public Collection<Game> findCurrentGames(){
		Collection<Game> games = new LinkedList<Game>();
		Collection<League> leagues = leagueService.findAll();
		for(League league: leagues){		
			Round round = roundService.nextRound(league.getId());	
			if(round!=null){
				Collection<Game> aux = gameRepository.findByRoundId(round.getId());
				games.addAll(aux);
			}					
		}
		return games;
	}
	
	public GameToListForm fragmentToList(Game game){
		GameToListForm gameToListForm = new GameToListForm();
		gameToListForm.setId(game.getId());
		gameToListForm.setAwayTeamId(game.getAwayTeam().getId());
		gameToListForm.setAwayTeamName(game.getAwayTeam().getName());
		gameToListForm.setFinishDate(game.getRound().getFinishDate());
		gameToListForm.setLeagueName(game.getRound().getSeason().getLeague().getName());
		gameToListForm.setHomeTeamId(game.getHomeTeam().getId());
		gameToListForm.setHomeTeamName(game.getHomeTeam().getName());
		gameToListForm.setRoundNumber(game.getRound().getRoundNumber());
		gameToListForm.setStartDate(game.getRound().getFinishDate());
		return gameToListForm;
	}
	
	public Collection<GameToListForm> fragmentsToList(Collection<Game> games){
		Collection<GameToListForm> gamesToList = new LinkedList<GameToListForm>();
		for(Game game : games){
			GameToListForm gameToListForm = fragmentToList(game);
			gamesToList.add(gameToListForm);
		}
		return gamesToList;
	}
	
	public Game findByGameId(Integer gameId){
		return gameRepository.findOne(gameId);
	}
	
	
}
