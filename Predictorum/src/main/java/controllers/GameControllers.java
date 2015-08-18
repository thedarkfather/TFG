package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.GameService;
import domain.Game;
import forms.GameToListForm;

@Controller
@RequestMapping("/game")
public class GameControllers extends AbstractController{
	
	@Autowired
	private GameService gameService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/listNextGames")
	public Collection<GameToListForm> findAll() {
		Collection<Game> games = gameService.findCurrentGames();
		Collection<GameToListForm> gamesToList = gameService.fragmentsToList(games);
		return gamesToList;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findOne/{gameId}")
	public GameToListForm findOne(@PathVariable Integer gameId) {
		Game game = gameService.findByGameId(gameId);
		GameToListForm gameToListForm = gameService.fragmentToList(game);
		return gameToListForm;
	}
	
}
