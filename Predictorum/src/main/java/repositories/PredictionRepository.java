package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prediction;

@Repository 
public interface PredictionRepository extends JpaRepository<Prediction,Integer>{

	@Query("select p from Prediction p where p.game.id = ?1 and p.user=null")
	Prediction findSystemPredictionByGameId(Integer gameId);

	@Query("select p from Prediction p where p.user.id = ?1")
	Collection<Prediction> findToListByUserId(Integer userId);

	@Query("select p from Prediction p where p.game.id = ?1 and p.user!=null")
	Collection<Prediction> findUserPredictionsByGameId(Integer gameId);
	
	@Query("select p from Prediction p where p.user.id = ?1 and p.game.id = ?2")
	Prediction findPredictionByPrincipalAndGameId(Integer userId, Integer gameId);

}
