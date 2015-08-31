package repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Round;

@Repository 
public interface RoundRepository extends JpaRepository<Round,Integer> {

	@Query("select r from Round r where r.season.id=?1 and r.roundNumber=?2 and r.updated=false and r.finishDate < CURRENT_TIMESTAMP")
	Round findNoUpdatedBySeasonIdAndRoundNumber(Integer seasonId,Integer roundNumber);
	
	//la siguiente jornada
	@Query("select r from Round r where r.season.league.id=?1 and r.updated=false and r.finishDate >= CURRENT_TIMESTAMP order by r.roundNumber asc")
	List<Round> nextRounds(Integer leagueId);											
}
