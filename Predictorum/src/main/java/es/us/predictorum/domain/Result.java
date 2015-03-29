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
 * A Result.
 */
@Entity
@Table(name = "T_RESULT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0)
    @Column(name = "home_goals")
    private Integer homeGoals;

    @Min(value = 0)
    @Column(name = "away_goals")
    private Integer awayGoals;

    @Min(value = 0)
    @Column(name = "half_home_goals")
    private Integer halfHomeGoals;

    @Min(value = 0)
    @Column(name = "half_away_goals")
    private Integer halfAwayGoals;

    @OneToOne
    private Match match;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Integer getHalfHomeGoals() {
        return halfHomeGoals;
    }

    public void setHalfHomeGoals(Integer halfHomeGoals) {
        this.halfHomeGoals = halfHomeGoals;
    }

    public Integer getHalfAwayGoals() {
        return halfAwayGoals;
    }

    public void setHalfAwayGoals(Integer halfAwayGoals) {
        this.halfAwayGoals = halfAwayGoals;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Result result = (Result) o;

        if ( ! Objects.equals(id, result.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", homeGoals='" + homeGoals + "'" +
                ", awayGoals='" + awayGoals + "'" +
                ", halfHomeGoals='" + halfHomeGoals + "'" +
                ", halfAwayGoals='" + halfAwayGoals + "'" +
                '}';
    }
}
