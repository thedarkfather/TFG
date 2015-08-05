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

import domain.Comment;
import domain.Evaluation;
import forms.EvaluationForm;
import forms.CommentForm;
import forms.CommentToListForm;
import responses.GeneralResponse;
import services.CommentService;
import services.EvaluationService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{predictionId}")
	public Collection<CommentToListForm> findCommentByPredictionId(@PathVariable Integer predictionId) {
		Collection<Comment> comments = commentService.findByPrediciontId(predictionId);
		Collection<CommentToListForm>  commentToListForms = commentService.reconstructs(comments);
		return commentToListForms;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse comment(@RequestBody @Valid CommentForm commentForm, BindingResult binding){
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(commentForm, binding));
		} else {
			try{
				Comment comment = commentService.reconstructToSave(commentForm);
				commentService.save(comment);
				generalResponse = new GeneralResponse(true, new HashMap<String, String>());			
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse evaluate(@RequestBody @Valid EvaluationForm evaluationForm, BindingResult binding){
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(evaluationForm, binding));
		} else {
			try{
				Evaluation evaluation = evaluationService.reconstructToSave(evaluationForm);
				evaluationService.save(evaluation);
				generalResponse = new GeneralResponse(true, new HashMap<String, String>());			
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/evaluation/delete/{commentId}")
	public GeneralResponse deleteEvaluation(@PathVariable Integer commentId) {
		GeneralResponse generalResponse;	
		try {
			Evaluation evaluation = evaluationService.findByPrincipalAndCommentId(commentId);
			evaluationService.delete(evaluation);
			generalResponse = new GeneralResponse(true,	new HashMap<String, String>());
		} catch (Throwable oops) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("fail", "You can not commit this operation");
			generalResponse = new GeneralResponse(false, errors);
		}
		return generalResponse;
	}
	
	
	
}
