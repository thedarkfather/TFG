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
import domain.Game;
import domain.Prediction;
import domain.Result;
import domain.Team;
import domain.User;
import forms.EditImageUserForm;
import forms.EditUserForm;
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
	
	public User reconstructToChangeImage(EditImageUserForm editImageUserForm){
		User user = findByPrincipal();
		user.setLogo(editImageUserForm.getImage());
		return user;
	}
	
	public User reconstructJoin(JoinForm joinForm) {
		User u = new User();
		u.setaGPoints(0);
		u.setaGPointsPossible(0);
		u.setComments(new LinkedList<Comment>());
		u.setEmail(null);
		u.setEvaluations(new LinkedList<Evaluation>());
		u.setFollowers(new LinkedList<User>());
		u.setFollowing(new LinkedList<User>());
		u.sethGPoints(0);
		u.sethGPointsPossible(0);
		u.setLogo(null);
		u.setmT25Points(0);
		u.setmT25PointsPossible(0);
		u.setName(null);		
		u.setPredictions(new LinkedList<Prediction>());
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
				Integer points = user.getaGPoints() + user.getdRPoints() + user.gethGPoints() + user.getmT25Points() + user.getsRPoints();
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
		Integer points = user.getaGPoints() + user.getdRPoints() + user.gethGPoints() + user.getmT25Points() + user.getsRPoints();
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
	
	public UserDetailsForm getProfile(Integer userId) {
		User user = null;
		if(userId==null){
			user  = findByPrincipal();
		}else{
			user = userRepository.findOne(userId);
		}		
		Integer cero = 0;
		UserDetailsForm userDetailsForm = new UserDetailsForm();
		
		if(!cero.equals(user.getaGPointsPossible())){
			userDetailsForm.setaGPointsPercentaje((user.getaGPoints()*100)/user.getaGPointsPossible());
		}else{
			userDetailsForm.setaGPointsPercentaje(cero);
		}		
		
		if(!cero.equals(user.gethGPointsPossible())){
			userDetailsForm.sethGPointsPercentaje((user.gethGPoints()*100)/user.gethGPointsPossible());
		}else{
			userDetailsForm.sethGPointsPercentaje(cero);
		}
		
		if(!cero.equals(user.getsRPointsPossible())){
			userDetailsForm.setsRPointsPercentaje((user.getsRPoints()*100)/user.getsRPointsPossible());
		}else{
			userDetailsForm.setsRPointsPercentaje(cero);
		}
		
		if(!cero.equals(user.getmT25PointsPossible())){
			userDetailsForm.setmT25PointsPercentaje((user.getmT25Points()*100)/user.getmT25PointsPossible());
		}else{
			userDetailsForm.setmT25PointsPercentaje(cero);
		}
		
		if(!cero.equals(user.getdRPointsPossible())){
			userDetailsForm.setdRPointsPercentaje((user.getdRPoints()*100)/user.getdRPointsPossible());
		}else{
			userDetailsForm.setdRPointsPercentaje(cero);
		}
		
		userDetailsForm.setFollowersNumber(user.getFollowers().size());
		userDetailsForm.setFollowingNumber(user.getFollowing().size());			
		Collection<PredictionToListForm> predictionsToListForm = predictionService.findToListByUserId(user.getId());
		userDetailsForm.setPredictions(predictionsToListForm);
		Integer rankingPosition = getPosition(user);
		Assert.notNull(rankingPosition);
		userDetailsForm.setRankingPosition(rankingPosition);		
		userDetailsForm.setUsername(user.getUserAccount().getUsername());
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

	public void updatePointsByResult(Result result) {
		Assert.notNull(result);
		Game game = result.getGame();
		Collection<Prediction> predictions = predictionService.findUserPredictionsByGameId(game.getId());
		for(Prediction prediction : predictions){
			User user = prediction.getUser();
			
			String sResult;
			if(result.getHomeGoals().compareTo(result.getAwayGoals())>0){
				sResult="1";
			}else if(result.getHomeGoals().equals(result.getAwayGoals())){
				sResult="X";
			}else{
				sResult="2";
			}
			
			//Actualizamos los puntos por resultado simple		
			if(prediction.getSimpleResult().equals(sResult)){
				user.setsRPoints(user.getsRPoints()+1);
			}
			
			//Actualizamos los puntos por resultado double
			if(prediction.getDoubleResult().contains(sResult)){
				user.setdRPoints(user.getdRPoints()+1);
			}			
			
			//Actualiamos los puntos por home goals
			if(prediction.getHomeGoals().equals(result.getHomeGoals())){
				user.sethGPoints(user.gethGPoints()+1);
			}
			
			//Actualizamos los puntos por away goals
			if(prediction.getAwayGoals().equals(result.getAwayGoals())){
				user.setaGPoints(user.getaGPoints()+1);
			}
			
			//Actualizamos lo puntos por mas de 2.5 goals
			Integer totalGoals = result.getHomeGoals()+result.getAwayGoals();
			if(prediction.getMoreThan25() && totalGoals.compareTo(2)>0){
				user.setmT25Points(user.getmT25Points()+1);
			}else if(!prediction.getMoreThan25() && totalGoals.compareTo(2)<=0){
				user.setmT25Points(user.getmT25Points()+1);
			}
			
			user.setsRPointsPossible(user.getsRPointsPossible()+1);
			user.setdRPointsPossible(user.getdRPointsPossible()+1);
			user.sethGPointsPossible(user.gethGPointsPossible()+1);
			user.setaGPointsPossible(user.getaGPointsPossible()+1);
			user.setmT25PointsPossible(user.getmT25PointsPossible());			
			save(user);
		}		
	}

	public void editUser(EditUserForm editUserForm) {
		Assert.notNull(editUserForm);
		User principal = findByPrincipal();
		principal.setEmail(editUserForm.getEmail());
		if(!wrongChangePassword(editUserForm)){
			UserAccount ua = principal.getUserAccount();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(editUserForm.getPassword(), null);
			ua.setPassword(hash);
		}
		save(principal);		
	}

	public boolean wrongChangePassword(EditUserForm editUserForm) {
		return editUserForm.getPassword()!=null && editUserForm.getRepassword()!=null && !editUserForm.getPassword().equals(editUserForm.getRepassword());
	}

}
