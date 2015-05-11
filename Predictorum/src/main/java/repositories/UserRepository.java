package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	@Query("select u from User u where u.userAccount.username=?1")
	Collection<User> findByUsername(String user1Username);

	@Query("select u from User u where u.userAccount.id=?1")
	User findOneByUserAccount(int id);

	@Query("select uf from User u join u.followers uf where u.userAccount.id=?1 group by uf")
	Collection<User> findFollowersByUserId(Integer id);

	@Query("select uf from User u join u.following uf where u.userAccount.id=?1 group by uf")
	Collection<User> findFollowingByUserId(int id);

	@Query("select u from User u join u.following uf where uf.id=?1 and u.id=?2 group by u")
	User findIsFollowed(Integer userId, Integer principalId);

}
