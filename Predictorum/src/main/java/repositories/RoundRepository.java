package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Round;

@Repository 
public interface RoundRepository extends JpaRepository<Round,Integer> {

}
