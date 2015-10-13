package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Game;
import domain.Prediction;
import domain.User;
import forms.PredictionForm;
import forms.PredictionFormToSave;
import forms.PredictionToListForm;
import repositories.PredictionRepository;

@Service
@Transactional
public class PredictionService {
	
	@Autowired
	private PredictionRepository predictionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private CommentService commentService;
	
	public Collection<Prediction> findAll(){
		Collection<Prediction> predictions = predictionRepository.findAll();
		return predictions;
	}

	public Prediction findSystemPredictionByGameId(Integer gameId) {
		Assert.notNull(gameId);
		return predictionRepository.findSystemPredictionByGameId(gameId);
	}
	
	public Collection<Prediction> findUserPredictionsByGameId(Integer gameId) {
		Assert.notNull(gameId);
		return predictionRepository.findUserPredictionsByGameId(gameId);
	}

	public PredictionForm systemReconstructsToForm(Prediction prediction) {
		Assert.notNull(prediction);
		PredictionForm predictionForm = new PredictionForm();
		predictionForm.setId(prediction.getId());
		predictionForm.setGameId(prediction.getGame().getId());
		predictionForm.setHomeTeamId(prediction.getGame().getHomeTeam().getId());
		predictionForm.setAwayTeamId(prediction.getGame().getAwayTeam().getId());
		predictionForm.setHomeName(prediction.getGame().getHomeTeam().getName());
		predictionForm.setAwayName(prediction.getGame().getAwayTeam().getName());
		//Atributos de prediction
		predictionForm.setSimpleResult(prediction.getSimpleResult());
		predictionForm.setpSimpleResult(100*prediction.getpSimpleResult());
		predictionForm.setDoubleResult(prediction.getDoubleResult());
		predictionForm.setpDoubleResult(100*prediction.getpDoubleResult());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(100*prediction.getpHomeGoals());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(100*prediction.getpAwayGoals());
		predictionForm.setMoreThan25(prediction.getMoreThan25());
		predictionForm.setPmoreThan25(100*prediction.getpMoreThan25());
		predictionForm.setCommentSize(commentService.findByPrediciontId(prediction.getId()).size());
		predictionForm.setStartDate(prediction.getGame().getRound().getStartDate());
		return predictionForm;
	}

	public Collection<PredictionToListForm> findToListByUserId(Integer userId) {
		Assert.notNull(userId);
		Collection<Prediction> predictions = predictionRepository.findToListByUserId(userId);
		Collection<PredictionToListForm> predictionsToListForm = new LinkedList<PredictionToListForm>();
		for(Prediction prediction: predictions){
			PredictionToListForm predictionToListForm = new PredictionToListForm();
			predictionToListForm.setAwayTeamGoals(prediction.getAwayGoals());
			predictionToListForm.setAwayTeamName(prediction.getGame().getAwayTeam().getName());
			predictionToListForm.setFinishDate(prediction.getGame().getRound().getFinishDate());
			predictionToListForm.setHomeTeamName(prediction.getGame().getHomeTeam().getName());
			predictionToListForm.setHomeTeamGoals(prediction.getHomeGoals());
			predictionToListForm.setId(prediction.getId());
			predictionsToListForm.add(predictionToListForm);
		}
		return predictionsToListForm;
	}

	public Collection<PredictionForm> userReconstructToForms(Collection<Prediction> predictions) {
		Collection<PredictionForm> predictionForms = new LinkedList<PredictionForm>();
		for(Prediction prediction: predictions){
			PredictionForm predictionForm = userReconstructToForm(prediction);
			predictionForms.add(predictionForm);			
		}
		return predictionForms;
	}

	public PredictionForm userReconstructToForm(Prediction prediction) {
		Assert.notNull(prediction);
		PredictionForm predictionForm = new PredictionForm();
		User user = prediction.getUser();
		Assert.notNull(user);
		predictionForm.setId(prediction.getId());
		predictionForm.setGameId(prediction.getGame().getId());
		predictionForm.setHomeTeamId(prediction.getGame().getHomeTeam().getId());
		predictionForm.setAwayTeamId(prediction.getGame().getAwayTeam().getId());
		predictionForm.setHomeName(prediction.getGame().getHomeTeam().getName());
		predictionForm.setAwayName(prediction.getGame().getAwayTeam().getName());
		//Atributos de prediction
		predictionForm.setSimpleResult(prediction.getSimpleResult());
		predictionForm.setpSimpleResult(100*prediction.getpSimpleResult());
		predictionForm.setDoubleResult(prediction.getDoubleResult());
		predictionForm.setpDoubleResult(100*prediction.getpDoubleResult());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(100*prediction.getpHomeGoals());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(100*prediction.getpAwayGoals());
		predictionForm.setMoreThan25(prediction.getMoreThan25());
		predictionForm.setPmoreThan25(100*prediction.getpMoreThan25());
		predictionForm.setCommentSize(commentService.findByPrediciontId(prediction.getId()).size());
		predictionForm.setUsername(prediction.getUser().getUserAccount().getUsername());
		predictionForm.setStartDate(prediction.getGame().getRound().getStartDate());
		return predictionForm;
	}
	
	public PredictionFormToSave userReconstructFormToSave(Prediction prediction) {
		PredictionFormToSave predictionFormToSave  = new PredictionFormToSave();
		if(prediction!=null){
			predictionFormToSave.setAwayGoals(prediction.getAwayGoals());
			predictionFormToSave.setDoubleResult(prediction.getDoubleResult());
			predictionFormToSave.setGameId(prediction.getGame().getId());
			predictionFormToSave.setHomeGoals(prediction.getHomeGoals());
			predictionFormToSave.setMoreThan25(prediction.getMoreThan25());
			predictionFormToSave.setSimpleResult(prediction.getSimpleResult());
		}
		return predictionFormToSave;
	}
	
	public Prediction reconstructToSaveUserPrediction(PredictionFormToSave predictionFormToSave){
		Prediction prediction = new Prediction();	
		Prediction predictionAux = findPredictionByPrincipalAndGameId(predictionFormToSave.getGameId());
		if(predictionAux!=null){
			prediction = predictionAux;
		}
		User user = userService.findByPrincipal();
		Assert.notNull(user);
		prediction.setUser(user);
		Game game = gameService.findByGameId(predictionFormToSave.getGameId());
		Assert.notNull(game);
		prediction.setGame(game);
		prediction.setComments(new LinkedList<Comment>());		
		prediction.setSimpleResult(predictionFormToSave.getSimpleResult());
		//Con los if controlamos que los puntos posibles sean 0
		if(user.getsRPointsPossible().equals(0)){
			prediction.setpSimpleResult(0.0);
		}else{
			prediction.setpSimpleResult(new Double(user.getsRPoints())/user.getsRPointsPossible());
		}				
		prediction.setDoubleResult(predictionFormToSave.getDoubleResult());
		if(user.getdRPointsPossible().equals(0)){
			prediction.setpDoubleResult(0.0);
		}else{
			prediction.setpDoubleResult(new Double(user.getdRPoints())/user.getdRPointsPossible());
		}				
		prediction.setHomeGoals(predictionFormToSave.getHomeGoals());
		if(user.gethGPointsPossible().equals(0)){
			prediction.setpHomeGoals(0.0);
		}else{
			prediction.setpHomeGoals(new Double(user.gethGPoints())/user.gethGPointsPossible());	
		}			
		prediction.setAwayGoals(predictionFormToSave.getAwayGoals());
		if(user.getaGPointsPossible().equals(0)){
			prediction.setpAwayGoals(0.0);
		}else{
			prediction.setpAwayGoals(new Double(user.getaGPoints())/user.getaGPointsPossible());
		}				
		prediction.setMoreThan25(predictionFormToSave.getMoreThan25());
		if(user.getmT25PointsPossible().equals(0)){
			prediction.setpMoreThan25(0.0);
		}else{
			prediction.setpMoreThan25(new Double(user.getmT25Points())/user.getmT25PointsPossible());	
		}			
		return prediction;		
	}
	
	public void save(Prediction prediction){
		Assert.notNull(prediction);
		Date current = new Date(System.currentTimeMillis());
		Date roundStartDate = prediction.getGame().getRound().getStartDate();
		//La fecha de los partidos a�n no haya comenzado
		Assert.isTrue(roundStartDate.compareTo(current)>0);
		predictionRepository.save(prediction);
	}
	
	public void saveSystem(Prediction prediction){
		Assert.notNull(prediction);
		predictionRepository.save(prediction);
	}
	
	public Prediction findPredictionByPrincipalAndGameId(Integer gameId){
		User principal = userService.findByPrincipal();
		Prediction prediction = predictionRepository.findPredictionByPrincipalAndGameId(principal.getId(),gameId);
		return prediction;
	}

	public Prediction findById(Integer predictionId) {
		Assert.notNull(predictionId);
		return predictionRepository.findOne(predictionId);
	}
	
	public Boolean checkPredictionAtributes(Prediction prediction) {
		Boolean res = prediction.getSimpleResult()!=null || prediction.getDoubleResult()!=null || prediction.getAwayGoals()!=null || prediction.getHomeGoals()!=null || prediction.getMoreThan25()!=null;
		return res;
	}
	
}
