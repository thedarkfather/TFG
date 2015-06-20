package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Season;

@Repository 
public interface SeasonRepository extends JpaRepository<Season,Integer> {
	

}

