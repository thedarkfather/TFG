package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Prediction;
import domain.User;
import forms.PredictionForm;
import forms.PredictionToListForm;
import repositories.PredictionRepository;

@Service
@Transactional
public class PredictionService {
	
	@Autowired
	private PredictionRepository predictionRepository;
	
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
		predictionForm.setpSimpleResult(new Double(user.getsRPoints())/user.getsRPointsPossible());
		predictionForm.setDoubleResult(prediction.getDoubleResult());
		predictionForm.setpDoubleResult(new Double(user.getdRPoints())/user.getdRPointsPossible());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(new Double(user.gethGPoints())/user.gethGPointsPossible());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(new Double(user.getaGPoints())/user.getaGPointsPossible());
		return predictionForm;
	}

	public Prediction findById(Integer predictionId) {
		return predictionRepository.findOne(predictionId);
	}
	
}
