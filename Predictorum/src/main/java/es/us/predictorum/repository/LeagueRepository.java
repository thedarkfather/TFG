package es.us.predictorum.repository;

import es.us.predictorum.domain.League;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the League entity.
 */
public interface LeagueRepository extends JpaRepository<League,Long> {

}
