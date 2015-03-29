package es.us.predictorum.repository;

import es.us.predictorum.domain.Result;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Result entity.
 */
public interface ResultRepository extends JpaRepository<Result,Long> {

}
