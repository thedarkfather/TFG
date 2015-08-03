package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.username=?1")
	Collection<User> findByUsername(String user1Username);

	@Query("select u from User u where u.userAccount.id=?1")
	User findOneByUserAccount(int id);

	@Query("select uf from User u join u.followers uf where u.userAccount.id=?1 group by uf")
	Collection<User> findFollowersByUserId(Integer id);

	@Query("select uf from User u join u.following uf where u.userAccount.id=?1 group by uf")
	Collection<User> findFollowingByUserId(Integer userId);

	@Query("select u from User u join u.following uf where uf.id=?1 and u.id=?2 group by uf")
	User findIsFollowed(Integer userId, Integer principalId);

	@Query("select u from User u join u.teams t where t.id=?1 group by u")
	List<User> findFollowersByTeamId(Integer teamId);

	@Query("select u from User u order by (u.sRPoints + u.dRPoints + u.hGPoints + u.aGPoints + u.mT25Points) desc")
	List<User> findRankedUsers();
	
	@Query("select u from User u where u.userAccount.username like %?1% group by u")
	Collection<User> findUserByString(String userName);

}
