package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Season;

@Repository 
public interface SeasonRepository extends JpaRepository<Season,Integer> {

	@Query("select s from Season s where s.league.id = ?1")
	Season findByLeagueId(Integer leagueId);
	

}

