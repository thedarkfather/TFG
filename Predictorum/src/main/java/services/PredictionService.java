package services;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Prediction;
import forms.PredictionToList;
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
	
	public PredictionToList reconstructToList(Prediction prediction){
		PredictionToList predictionToList = new PredictionToList();
		predictionToList.setPredictionId(prediction.getId());
		predictionToList.setLeagueId(prediction.getGame().getHomeTeam().getLeague().getId());
		predictionToList.setLeagueName(prediction.getGame().getHomeTeam().getLeague().getName());
		predictionToList.setHomeTeamId(prediction.getGame().getHomeTeam().getId());
		predictionToList.setHomeTeamName(prediction.getGame().getHomeTeam().getName());
		predictionToList.setAwayTeamId(prediction.getGame().getAwayTeam().getId());
		predictionToList.setAwayTeamName(prediction.getGame().getAwayTeam().getName());
		predictionToList.setGameDate(prediction.getGame().getDate());
		predictionToList.setPredictionResult(prediction.getSimpleResult());
		predictionToList.setProbability(prediction.getpSimpleResult());
		return predictionToList;
	}
	
	public Collection<PredictionToList> reconstructsToList(Collection<Prediction> predictions){
		Collection<PredictionToList> predictionsToList = new LinkedList<PredictionToList>();
		for(Prediction prediction : predictions){
			PredictionToList predictionToList = reconstructToList(prediction);
			predictionsToList.add(predictionToList);
		}
		return predictionsToList;
	}

}
