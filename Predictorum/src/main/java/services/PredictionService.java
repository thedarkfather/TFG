package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Prediction;
import forms.PredictionForm;
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

	public PredictionForm reconstructsToForm(Prediction prediction) {
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
		predictionForm.setSimpleHalfResult(prediction.getSimpleHalfResult());
		predictionForm.setpSimpeHalfResult(prediction.getpSimpeHalfResult());
		predictionForm.setDoubleHalfResult(prediction.getDoubleHalfResult());
		predictionForm.setpDoubleHalfResult(prediction.getpDoubleHalfResult());
		predictionForm.setHomeGoals(prediction.getHomeGoals());
		predictionForm.setpHomeGoals(prediction.getpHomeGoals());
		predictionForm.setAwayGoals(prediction.getAwayGoals());
		predictionForm.setpAwayGoals(prediction.getpAwayGoals());
		return predictionForm;
	}
}
