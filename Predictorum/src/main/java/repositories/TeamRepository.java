package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Team;

public interface TeamRepository extends JpaRepository<Team,Integer> {

	@Query("select t from Team t join t.users u where u.id=?1")
	Collection<Team> findByPrincipal(Integer principalId);

}
