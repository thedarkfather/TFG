package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository 
public interface CommentRepository extends JpaRepository<Comment,Integer>{
	
	@Query("select c from Comment c where c.prediction.id=?1 and c.parent=null")
	Collection<Comment> findByPredictionId(Integer predictionId);

	@Query("select c from Comment c where c.parent.id=?1")
	Collection<Comment> findByParentId(Integer commentId);

}