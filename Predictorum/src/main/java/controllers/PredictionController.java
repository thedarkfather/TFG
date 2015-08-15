package controllers;




import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.Prediction;
import forms.PredictionForm;
import forms.PredictionFormToSave;
import responses.GeneralResponse;
import services.PredictionService;


@Controller
@RequestMapping("/prediction")
public class PredictionController extends AbstractController {

	@Autowired
	private PredictionService predictionService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/systemPrediction/{gameId}")
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/findOne/{predictionId}")
	public PredictionForm findOne(@PathVariable Integer predictionId) {
		Prediction prediction = predictionService.findById(predictionId);
		PredictionForm predictionForm = predictionService.userReconstructToForm(prediction);
		return predictionForm;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveUserPredicion", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse saveUserPredicion(@RequestBody @Valid PredictionFormToSave predictionFormToSave, BindingResult binding){
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(predictionFormToSave, binding));
		} else {
			try{
				Prediction prediction = predictionService.reconstructToSaveUserPrediction(predictionFormToSave);
				predictionService.save(prediction);
				generalResponse = new GeneralResponse(true, new HashMap<String, String>());			
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}


}
