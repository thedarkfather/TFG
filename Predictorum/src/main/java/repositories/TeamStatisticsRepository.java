package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TeamStatistics;

@Repository
public interface TeamStatisticsRepository extends JpaRepository<TeamStatistics,Integer>{

	@Query("select ts from TeamStatistics ts where ts.team.id = ?1")
	TeamStatistics findByTeamId(Integer teamId);
	
	@Query("select ts from TeamStatistics ts where ts.team.season.id = ?1 order by ts.points desc")
	List<TeamStatistics> findOrderByPoints(Integer seasonId);

}
