package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.PredictionService;

import domain.Prediction;

import forms.PredictionToList;

@Controller
@RequestMapping("/prediction")
public class PredictionController extends AbstractController {

	@Autowired
	private PredictionService predictionService;

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public Collection<PredictionToList> findAll() {
		Collection<Prediction> predictions = predictionService.findAll();
		Collection<PredictionToList> predictionsToList = predictionService
				.reconstructsToList(predictions);
		return predictionsToList;
	}

}
