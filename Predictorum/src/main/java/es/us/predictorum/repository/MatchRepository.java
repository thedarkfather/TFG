package es.us.predictorum.repository;

import es.us.predictorum.domain.Match;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Match entity.
 */
public interface MatchRepository extends JpaRepository<Match,Long> {

}
