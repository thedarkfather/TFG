package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Comment;
import domain.Evaluation;
import domain.Prediction;
import domain.Team;
import domain.User;
import forms.JoinForm;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User reconstructJoin(JoinForm joinForm) {
		User u = new User();
		u.setaGPoints(0);
		u.setComments(new LinkedList<Comment>());
		u.setdHRPoints(0);
		u.setEmail("");
		u.setEvaluations(new LinkedList<Evaluation>());
		u.setFollowers(new LinkedList<User>());
		u.setFollowing(new LinkedList<User>());
		u.sethAGPoints(0);
		u.sethGPoints(0);
		u.sethHGPoints(0);
		u.setLogo(null);
		u.setmT25Points(0);
		u.setName("");		
		u.setPredictions(new LinkedList<Prediction>());
		u.setsHRPoints(0);
		u.setsRPoints(0);
		u.setSurname("");
		u.setTeams(new LinkedList<Team>());
		
		UserAccount ua = new UserAccount();
		Authority authority = new Authority();
		Collection<Authority> authorities = new ArrayList<Authority>();		
		authority.setAuthority(Authority.USER);
		authorities.add(authority);		
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(joinForm.getPassword(), null);
		ua.setPassword(hash);


		ua.setUsername(joinForm.getUsername());
		ua.setPassword(joinForm.getPassword());
		ua.setAuthorities(authorities);
		
		u.setUserAccount(ua);
		
		return u;
	}
	
	public void save(User user){
		Assert.notNull(user);
		userRepository.save(user);		
	}
	
	//checks
	public boolean checkUniqueUsername(JoinForm joinForm){
		Boolean res = true;
		String user1Username = joinForm.getUsername();
		Collection<User> users = null;
		users = userRepository.findByUsername(user1Username);
		if(!users.isEmpty()){
			res = false;
		}
		return res;
	}
	
	public boolean checkPassword(JoinForm joinForm){
		return joinForm.getPassword().equals(joinForm.getRpassword());		
	}

	
}
