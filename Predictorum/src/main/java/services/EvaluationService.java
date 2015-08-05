package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Evaluation;
import domain.User;
import forms.EvaluationForm;
import repositories.EvaluationRepository;

@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRespository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	public void save(Evaluation evaluation){
		Assert.notNull(evaluation);
		Comment comment = evaluation.getComment();
		Evaluation evaluationAux = findByPrincipalAndCommentId(comment.getId());
		Assert.isTrue(evaluationAux==null);
		evaluationRespository.save(evaluation);
		//Si es positivo la evalucion 
		if(evaluation.getType()){
			comment.setPosPoints(comment.getPosPoints()+1);
		}else{
			comment.setNegPoints(comment.getNegPoints()+1);
		}
		commentService.save(comment);
	}

	public Evaluation reconstructToSave(EvaluationForm evaluationForm) {
		Evaluation evaluation = new Evaluation();
		Comment comment = commentService.findById(evaluationForm.getCommentId());
		Assert.notNull(comment);
		evaluation.setComment(comment);
		User principal = userService.findByPrincipal();
		evaluation.setUser(principal);
		evaluation.setType(evaluationForm.getType());
		return evaluation;		
	}
	
	public void delete(Evaluation evaluation){
		Assert.notNull(evaluation);
		//El controlador nos devuelve la evaluation del principal sobre el comentario y por eso no se realiza la comprobacion
		evaluationRespository.delete(evaluation);
	}
	
	
	public Evaluation findByPrincipalAndCommentId(Integer commentId){
		Assert.notNull(commentId);
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		Evaluation evaluation = evaluationRespository.findByUserIdAndCommentId(principal.getId(),commentId);
		return evaluation;
	}
	

}
