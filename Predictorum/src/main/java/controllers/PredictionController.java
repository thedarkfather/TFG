package controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.PredictionService;


@Controller
@RequestMapping("/prediction")
public class PredictionController extends AbstractController {

	@Autowired
	private PredictionService predictionService;


}
