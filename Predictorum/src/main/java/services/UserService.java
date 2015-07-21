package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Evaluation;
import domain.Prediction;
import domain.Team;
import domain.User;
import forms.JoinForm;
import forms.PredictionToListForm;
import forms.UserDetailsForm;
import forms.UserToList;
import forms.UserToRank;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PredictionService predictionService;
	
	public User reconstructJoin(JoinForm joinForm) {
		User u = new User();
		u.setaGPoints(0);
		u.setaGPointsPossible(0);
		u.setComments(new LinkedList<Comment>());
		u.setdHRPoints(0);
		u.setdHRPointsPossible(0);
		u.setEmail(null);
		u.setEvaluations(new LinkedList<Evaluation>());
		u.setFollowers(new LinkedList<User>());
		u.setFollowing(new LinkedList<User>());
		u.sethAGPoints(0);
		u.sethAGPointsPossible(0);
		u.sethGPoints(0);
		u.sethGPointsPossible(0);
		u.sethHGPoints(0);
		u.sethHGPointsPossible(0);
		u.setLogo(null);
		u.setmT25Points(0);
		u.setmT25PointsPossible(0);
		u.setName(null);		
		u.setPredictions(new LinkedList<Prediction>());
		u.setsHRPoints(0);
		u.setsHRPointsPossible(0);
		u.setsRPoints(0);
		u.setsRPointsPossible(0);
		u.setSurname(null);
		u.setdRPoints(0);
		u.setdRPointsPossible(0);
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
		ua.setAuthorities(authorities);
		
		u.setUserAccount(ua);
		
		return u;
	}
	
	public void save(User user){
		Assert.notNull(user);
		userRepository.save(user);		
	}
	
	public Collection<User> findAll() {
		Collection<User> users = userRepository.findAll();
		return users;
	}
	
	public Collection<User> findFollowers() {
		User u = findByPrincipal();
		Collection<User> users = userRepository.findFollowersByUserId(u.getUserAccount().getId());
		return users;
	}
	

	public Collection<User> findFollowing() {
		User u = findByPrincipal();
		Collection<User> users = userRepository.findFollowingByUserId(u.getUserAccount().getId());
		return users;
	}
	
	public User findByPrincipal(){
		return userRepository.findOneByUserAccount(LoginService.getPrincipal().getId());
	}
	
	public List<UserToRank> findRankedUsers() {
		List<UserToRank> usersToRank = new LinkedList<UserToRank>();
		List<User> rankedUser = userRepository.findRankedUsers();
		int size = rankedUser.size();
		for(int i = 0; i<100;i++){
			if(i<size){
				User user = rankedUser.get(i);
				UserToRank userToRank = new UserToRank();
				userToRank.setFollowing(isFollowed(rankedUser.get(i)));
				userToRank.setId(user.getId());
				userToRank.setName(user.getUserAccount().getUsername());
				Integer points = user.getaGPoints() + user.getdHRPoints() + user.getdRPoints() + user.gethAGPoints() + user.gethGPoints() + user.gethHGPoints() + user.getmT25Points() + user.getsHRPoints() + user.getsRPoints();
				userToRank.setPoints(points);
				userToRank.setPosition(i+1);
				usersToRank.add(userToRank);
			}else{
				break;
			}
		}
		return usersToRank;
	}
	
	public Collection<UserToList> reconstructsToList(Collection<User> userAux) {
		Collection<UserToList> usersToList = new LinkedList<>();
		for(User u:userAux){
			UserToList uTL = reconstructToList(u);
			usersToList.add(uTL);
		}
		return usersToList;
	}
	
	public UserToList reconstructToList(User user) {
		UserToList userToList = new UserToList();
		userToList.setId(user.getId());
		userToList.setName(user.getUserAccount().getUsername());		
		Integer points = user.getaGPoints() + user.getdHRPoints() + user.getdRPoints() + user.gethAGPoints() + user.gethGPoints() + user.gethHGPoints() + user.getmT25Points() + user.getsHRPoints() + user.getsRPoints();
		userToList.setPoints(points);
		userToList.setFollowing(isFollowed(user));
		return userToList;
	}
	
	public boolean isFollowed(User user){
		User principal = findByPrincipal();
		User aux = userRepository.findIsFollowed(user.getId(), principal.getId());
		boolean res = aux!=null;
		return res;
	}

	public void followUser(Integer userId) {
		Assert.notNull(userId);
		User principal = findByPrincipal();
		User user = userRepository.findOne(userId);
		Boolean isBeingFollowed = isFollowed(user);
		Collection<User> users = userRepository.findFollowersByTeamId(userId);
		if(!isBeingFollowed){
			//Sigo al equip	
			users.add(principal);			
		}else{
			//Dejo de seguirlo
			users.remove(principal);
		}
		user.setFollowers(users);
		userRepository.save(user);			
	}
	
	public UserDetailsForm getProfile() {
		User principal  = findByPrincipal();
		Integer cero = 0;
		UserDetailsForm userDetailsForm = new UserDetailsForm();
		if(!cero.equals(principal.getaGPointsPossible())){
			userDetailsForm.setaGPointsPercentaje(principal.getaGPointsPossible()/principal.getaGPoints());
		}else{
			userDetailsForm.setaGPointsPercentaje(cero);
		}		
		if(!cero.equals(principal.getdHRPointsPossible())){
			userDetailsForm.setdHRPointsPercentaje(principal.getdHRPointsPossible()/principal.getdHRPoints());
		}else{
			userDetailsForm.setdHRPointsPercentaje(cero);
		}
		if(!cero.equals(principal.getdRPointsPossible())){
			userDetailsForm.setdRPointsPercentaje(principal.getdRPointsPossible()/principal.getdHRPoints());
		}else{
			userDetailsForm.setdRPointsPercentaje(cero);
		}
		if(!cero.equals(principal.gethAGPointsPossible())){
			userDetailsForm.sethAGPointsPercentaje(principal.gethAGPointsPossible()/principal.gethAGPoints());
		}else{
			userDetailsForm.sethAGPointsPercentaje(cero);
		}
		if(!cero.equals(principal.gethGPointsPossible())){
			userDetailsForm.sethGPointsPercentaje(principal.gethGPointsPossible()/principal.gethGPoints());
		}else{
			userDetailsForm.sethGPointsPercentaje(cero);
		}
		if(!cero.equals(principal.getsHRPointsPossible())){
			userDetailsForm.setsHRPointsPercentaje(principal.getsHRPointsPossible()/principal.getsHRPoints());
		}else{
			userDetailsForm.setsHRPointsPercentaje(cero);
		}
		if(!cero.equals(principal.getsRPointsPossible())){
			userDetailsForm.setsRPointsPercentaje(principal.getsRPointsPossible()/principal.getsRPoints());
		}else{
			userDetailsForm.setsRPointsPercentaje(cero);
		}
		if(!cero.equals(principal.getsRPointsPossible())){
			userDetailsForm.setmT25PointsPercentaje(principal.getmT25PointsPossible()/principal.getmT25Points());
		}else{
			userDetailsForm.setmT25PointsPercentaje(cero);
		}	
		userDetailsForm.setFollowersNumber(principal.getFollowers().size());
		userDetailsForm.setFollowingNumber(principal.getFollowing().size());			
		List<PredictionToListForm> predictionsToListForm = predictionService.findToListByUserId(principal.getId());
		userDetailsForm.setPredictions(predictionsToListForm);
		Integer rankingPosition = getPosition(principal);
		Assert.notNull(rankingPosition);
		userDetailsForm.setRankingPosition(rankingPosition);		
		userDetailsForm.setUsername(principal.getUserAccount().getUsername());
		return userDetailsForm;
	}
	
	private Integer getPosition(User principal) {
		Integer rankingPosition = null;
		List<User> rankedUser = userRepository.findRankedUsers();
		int size = rankedUser.size();
		for(int i = 0; i<size;i++){
			if(rankedUser.get(i).getId()==principal.getId()){
				rankingPosition = i+1;
				break;
			}
		}
		return rankingPosition;
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

	public List<User> findFollowersByTeamId(Integer teamId) {
		Assert.notNull(teamId);
		return userRepository.findFollowersByTeamId(teamId);
	}

	public Collection<User> findUserByString(String cadena) {
		return userRepository.findUserByString(cadena);
	}

}
