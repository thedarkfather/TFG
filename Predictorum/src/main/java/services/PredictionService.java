package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Prediction;
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
}
