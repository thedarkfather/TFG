package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
		predictionForm.setHomeTeamId(prediction.getGame().getHomeTeam().getId());
		predictionForm.setAwayTeamId(prediction.getGame().getAwayTeam().getId());
		predictionForm.setHomeName(prediction.getGame().getHomeTeam().getName());
		predictionForm.setAwayName(prediction.getGame().getAwayTeam().getName());
		//Atributos de prediction
		predictionForm.setSimpleResult(prediction.getSimpleResult());
		predictionForm.setpSimpleResult(prediction.getpSimpleResult());
		predictionForm.setDoubleResult(prediction.getDoubleResult());
		predictionForm.setpDoubleResult(prediction.getpDoubleResult());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(prediction.getpHomeGoals());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(prediction.getpAwayGoals());
		predictionForm.setMoreThan25(prediction.getMoreThan25());
		predictionForm.setPmoreThan25(prediction.getpMoreThan25());
		return predictionForm;
	}

	public List<PredictionToListForm> findToListByUserId(Integer userId) {
		Assert.notNull(userId);
		List<Prediction> predictions = predictionRepository.findToListByUserId(userId);
		List<PredictionToListForm> predictionsToListForm = new LinkedList<PredictionToListForm>();
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

	private PredictionForm userReconstructToForm(Prediction prediction) {
		Assert.notNull(prediction);
		PredictionForm predictionForm = new PredictionForm();
		User user = prediction.getUser();
		Assert.notNull(user);
		predictionForm.setHomeTeamId(prediction.getGame().getHomeTeam().getId());
		predictionForm.setAwayTeamId(prediction.getGame().getAwayTeam().getId());
		predictionForm.setHomeName(prediction.getGame().getHomeTeam().getName());
		predictionForm.setAwayName(prediction.getGame().getAwayTeam().getName());
		//Atributos de prediction
		predictionForm.setSimpleResult(prediction.getSimpleResult());
		predictionForm.setpSimpleResult(prediction.getpSimpleResult());
		predictionForm.setDoubleResult(prediction.getDoubleResult());
		predictionForm.setpDoubleResult(prediction.getpDoubleResult());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(prediction.getpHomeGoals());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(prediction.getpAwayGoals());
		predictionForm.setMoreThan25(prediction.getMoreThan25());
		predictionForm.setPmoreThan25(prediction.getpMoreThan25());
		return predictionForm;
	}
	
	public Prediction reconstructToSaveUserPrediction(PredictionFormToSave predictionFormToSave){
		Prediction prediction = new Prediction();		
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
		//No puede hacer dos predicciones sobre el mismo partido
		if(prediction.getUser()!=null){
			Prediction predictionAux = findPredictionByPrincipalAndGameId(prediction.getGame().getId());
			Assert.isNull(predictionAux);
		}
		predictionRepository.save(prediction);
	}
	
	public Prediction findPredictionByPrincipalAndGameId(Integer gameId){
		User principal = userService.findByPrincipal();
		Prediction prediction = predictionRepository.findPredictionByPrincipalAndGameId(principal.getId(),gameId);
		return prediction;
	}

	public Prediction findById(Integer predictionId) {
		return predictionRepository.findOne(predictionId);
	}
	
}
