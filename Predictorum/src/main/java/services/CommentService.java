package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Evaluation;
import domain.Prediction;
import domain.User;
import forms.CommentForm;
import forms.CommentToListForm;
import repositories.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PredictionService predictionService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	public Comment create(){
		Comment comment = new Comment();
		User principal = userService.findByPrincipal();
		comment.setUser(principal);
		comment.setDate(new Date(System.currentTimeMillis()-1000));
		comment.setChildren(new LinkedList<Comment>());
		comment.setEvaluations(new LinkedList<Evaluation>());
		comment.setPosPoints(0);
		comment.setNegPoints(0);
		return comment;
	}
	
	public Collection<Comment> findByPrediciontId(Integer predictionId){
		Assert.notNull(predictionId);
		Collection<Comment> comments = commentRepository.findByPredictionId(predictionId);
		return comments;
	}
	
	public void save(Comment comment){
		Assert.notNull(comment);
		commentRepository.save(comment);
	}
	
	
	//Aun no esta decidida su implementacion
	public void deletet(Comment comment){
		Assert.notNull(comment);
		User principal = userService.findByPrincipal();
		User commentUser = comment.getUser();
		Assert.isTrue(commentUser.getId()==principal.getId());
		commentRepository.delete(comment);
	}
	
	public Comment findById(Integer commentId){
		Assert.notNull(commentId);
		Comment comment = commentRepository.findOne(commentId);
		return comment;
	}
	
	public CommentToListForm reconstruct(Comment comment){
		Assert.notNull(comment);
		CommentToListForm commentToListForm = new CommentToListForm();
		commentToListForm.setId(comment.getId());
		commentToListForm.setNegPoints(comment.getNegPoints());
		commentToListForm.setPosPoints(comment.getPosPoints());
		commentToListForm.setText(comment.getText());
		commentToListForm.setDate(comment.getDate());
		Evaluation evaluation = evaluationService.findByPrincipalAndCommentId(comment.getId());
		if(evaluation==null){
			commentToListForm.setEvaluated(null);
		}else{
			commentToListForm.setEvaluated(evaluation.getType());
		}
		
		Comment commentAux = comment;
		Collection<CommentToListForm> children = new LinkedList<CommentToListForm>();
		Collection<Comment> childrens = commentRepository.findByParentId(commentAux.getId());
		for(Comment c : childrens){
				CommentToListForm aux = reconstruct(c);
				children.add(aux);
		}		
		commentToListForm.setChildren(children);		
		return commentToListForm;
	}
	
	public Collection<CommentToListForm> reconstructs(Collection<Comment>comments){
		Collection<CommentToListForm> commentToListForms = new LinkedList<CommentToListForm>();
		for(Comment comment:comments){
			CommentToListForm commentToListForm = reconstruct(comment);
			commentToListForms.add(commentToListForm);
		}
		return commentToListForms;
	}

	public Comment reconstructToSave(CommentForm commentForm) {
		Comment comment = create();
		if(commentForm.getParentId()!=null){
			Comment parent = findById(commentForm.getParentId());
			comment.setParent(parent);
		}
		Prediction prediction = predictionService.findById(commentForm.getPredictionId());
		Assert.notNull(prediction);
		comment.setPrediction(prediction);
		comment.setText(commentForm.getText());
		return comment;
	}

}
