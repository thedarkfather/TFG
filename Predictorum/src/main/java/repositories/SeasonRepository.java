package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Season;

@Repository 
public interface SeasonRepository extends JpaRepository<Season,Integer> {

	@Query("select s from Season s where s.league.id = ?1 and s.startDate < CURRENT_TIMESTAMP and s.finishDate >= CURRENT_TIMESTAMP")
	Season findCurrentByLeagueId(Integer leagueId);

	@Query("select s from Season s where s.league.id = ?1 ")
	Collection<Season> findByLeagueId(Integer leagueId);
	

}

