package es.us.predictorum.repository;

import es.us.predictorum.domain.Actor;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Actor entity.
 */
public interface ActorRepository extends JpaRepository<Actor,Long> {

    @Query("select actor from Actor actor where actor.owner.login = ?#{principal.username}")
    List<Actor> findAllForCurrentUser();

}
