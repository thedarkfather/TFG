package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity{
	
	private String name;
	private byte[] logo;
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte[] getLogo() {
		return logo;
	}
	
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	
	//Relaciones
	private Collection<User> users;
	private League league;
	private TeamStatistics teamStatistics;
	private Collection<Game> homeMatchs;
	private Collection<Game> awayMatchs;
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="teams")
	public Collection<User> getUsers() {
		return users;
	}
	
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public League getLeague() {
		return league;
	}
	
	public void setLeague(League league) {
		this.league = league;
	}
	
	@Valid
	@OneToOne(mappedBy="team")
	public TeamStatistics getTeamStatistics() {
		return teamStatistics;
	}
	
	public void setTeamStatistics(TeamStatistics teamStatistics) {
		this.teamStatistics = teamStatistics;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="homeTeam")
	public Collection<Game> getHomeMatchs() {
		return homeMatchs;
	}

	public void setHomeMatchs(Collection<Game> localMatchs) {
		this.homeMatchs = localMatchs;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="awayTeam")
	public Collection<Game> getAwayMatchs() {
		return awayMatchs;
	}

	public void setAwayMatchs(Collection<Game> awayMatchs) {
		this.awayMatchs = awayMatchs;
	}	
	
	

}
