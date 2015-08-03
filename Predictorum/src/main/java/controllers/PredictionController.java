package controllers;




import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.Prediction;
import forms.PredictionForm;
import services.PredictionService;


@Controller
@RequestMapping("/prediction")
public class PredictionController extends AbstractController {

	@Autowired
	private PredictionService predictionService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/systemPrediction/{gameIdId}")
	public PredictionForm findSystemPrediction(@PathVariable Integer gameId) {
		Prediction prediction = predictionService.findSystemPredictionByGameId(gameId);
		PredictionForm predictionForm = predictionService.systemReconstructsToForm(prediction);
		return predictionForm;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/userPredictions/{gameId}")
	public Collection<PredictionForm> findUserPredictions(@PathVariable Integer gameId) {
		Collection<Prediction> predictions = predictionService.findUserPredictionsByGameId(gameId);
		Collection<PredictionForm> predictionForms = predictionService.userReconstructToForms(predictions);
		return predictionForms;
	}


}
