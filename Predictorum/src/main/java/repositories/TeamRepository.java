package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Team;

public interface TeamRepository extends JpaRepository<Team,Integer> {

	@Query("select t from Team t join t.users u where t.id=?1 and u.id=?2")
	Team findIsFollowed(Integer teamId, Integer principalId);

	@Query("select t from Team t where t.season.id=?2 and t.name=?1")
	Team findByTeamNameAndSeasonId(String teamName, Integer seasonId);

	@Query("select t from Team t where t.season.startDate < CURRENT_TIMESTAMP and t.season.finishDate > CURRENT_TIMESTAMP")
	Collection<Team> findCurrentTeams();
	
	@Query("select distinct t.name from Team t")
	Collection<String> teamNames();

}
