package es.us.predictorum.repository;

import es.us.predictorum.domain.TeamStatistics;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TeamStatistics entity.
 */
public interface TeamStatisticsRepository extends JpaRepository<TeamStatistics,Long> {

}
