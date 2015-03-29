package es.us.predictorum.repository;

import es.us.predictorum.domain.Prediction;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Prediction entity.
 */
public interface PredictionRepository extends JpaRepository<Prediction,Long> {

}
