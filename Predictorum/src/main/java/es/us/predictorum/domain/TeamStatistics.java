package es.us.predictorum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TeamStatistics.
 */
@Entity
@Table(name = "T_TEAMSTATISTICS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamStatistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "streak", nullable = false)
    private String streak;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "won_match_percentage")
    private Integer wonMatchPercentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "lost_match_percentage")
    private Integer lostMatchPercentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "drawn_match_percentage")
    private Integer drawnMatchPercentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "more_than25_percentage")
    private Integer moreThan25Percentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "won_half_match_percentage")
    private Integer wonHalfMatchPercentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "lost_half_match_percentage")
    private Integer lostHalfMatchPercentage;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "draw_half_match_percentage")
    private Integer drawHalfMatchPercentage;

    @Min(value = 0)
    @Column(name = "home_won_matches")
    private Integer homeWonMatches;

    @Min(value = 0)
    @Column(name = "away_won_matches")
    private Integer awayWonMatches;

    @Min(value = 1)
    @Column(name = "league_position")
    private Integer leaguePosition;

    @Min(value = 0)
    @Column(name = "league_points")
    private Integer leaguePoints;

    @OneToOne
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public Integer getWonMatchPercentage() {
        return wonMatchPercentage;
    }

    public void setWonMatchPercentage(Integer wonMatchPercentage) {
        this.wonMatchPercentage = wonMatchPercentage;
    }

    public Integer getLostMatchPercentage() {
        return lostMatchPercentage;
    }

    public void setLostMatchPercentage(Integer lostMatchPercentage) {
        this.lostMatchPercentage = lostMatchPercentage;
    }

    public Integer getDrawnMatchPercentage() {
        return drawnMatchPercentage;
    }

    public void setDrawnMatchPercentage(Integer drawnMatchPercentage) {
        this.drawnMatchPercentage = drawnMatchPercentage;
    }

    public Integer getMoreThan25Percentage() {
        return moreThan25Percentage;
    }

    public void setMoreThan25Percentage(Integer moreThan25Percentage) {
        this.moreThan25Percentage = moreThan25Percentage;
    }

    public Integer getWonHalfMatchPercentage() {
        return wonHalfMatchPercentage;
    }

    public void setWonHalfMatchPercentage(Integer wonHalfMatchPercentage) {
        this.wonHalfMatchPercentage = wonHalfMatchPercentage;
    }

    public Integer getLostHalfMatchPercentage() {
        return lostHalfMatchPercentage;
    }

    public void setLostHalfMatchPercentage(Integer lostHalfMatchPercentage) {
        this.lostHalfMatchPercentage = lostHalfMatchPercentage;
    }

    public Integer getDrawHalfMatchPercentage() {
        return drawHalfMatchPercentage;
    }

    public void setDrawHalfMatchPercentage(Integer drawHalfMatchPercentage) {
        this.drawHalfMatchPercentage = drawHalfMatchPercentage;
    }

    public Integer getHomeWonMatches() {
        return homeWonMatches;
    }

    public void setHomeWonMatches(Integer homeWonMatches) {
        this.homeWonMatches = homeWonMatches;
    }

    public Integer getAwayWonMatches() {
        return awayWonMatches;
    }

    public void setAwayWonMatches(Integer awayWonMatches) {
        this.awayWonMatches = awayWonMatches;
    }

    public Integer getLeaguePosition() {
        return leaguePosition;
    }

    public void setLeaguePosition(Integer leaguePosition) {
        this.leaguePosition = leaguePosition;
    }

    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamStatistics teamStatistics = (TeamStatistics) o;

        if ( ! Objects.equals(id, teamStatistics.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TeamStatistics{" +
                "id=" + id +
                ", streak='" + streak + "'" +
                ", wonMatchPercentage='" + wonMatchPercentage + "'" +
                ", lostMatchPercentage='" + lostMatchPercentage + "'" +
                ", drawnMatchPercentage='" + drawnMatchPercentage + "'" +
                ", moreThan25Percentage='" + moreThan25Percentage + "'" +
                ", wonHalfMatchPercentage='" + wonHalfMatchPercentage + "'" +
                ", lostHalfMatchPercentage='" + lostHalfMatchPercentage + "'" +
                ", drawHalfMatchPercentage='" + drawHalfMatchPercentage + "'" +
                ", homeWonMatches='" + homeWonMatches + "'" +
                ", awayWonMatches='" + awayWonMatches + "'" +
                ", leaguePosition='" + leaguePosition + "'" +
                ", leaguePoints='" + leaguePoints + "'" +
                '}';
    }
}
