package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GameRepository;
import domain.Game;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	
	public Game saveEasy(Game game){
		Assert.notNull(game);
		return gameRepository.save(game);
		
	}
}
