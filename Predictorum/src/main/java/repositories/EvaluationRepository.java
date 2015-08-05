package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Evaluation;

@Repository 
public interface EvaluationRepository extends JpaRepository<Evaluation,Integer>{
	
	@Query("select e from Evaluation e where e.comment.id=?1")
	Collection<Evaluation> findByCommentId(Integer commentId);

	@Query("select e from Evaluation e where e.user.id=?1 and e.comment.id=?2")
	Evaluation findByUserIdAndCommentId(Integer userId, Integer commentId);

}
