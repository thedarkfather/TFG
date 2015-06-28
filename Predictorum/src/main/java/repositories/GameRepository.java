package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Game;

@Repository 
public interface GameRepository extends JpaRepository<Game,Integer>{

	@Query("select g from Game g where g.round.id = ?1 and g.homeTeam.name=?2 and g.awayTeam.name=?3")
	Game findByRoundIdAndLocalTeamAndAwayTeam(Integer roundId,String localTeam, String awayTeam);

}
