package es.us.predictorum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Prediction.
 */
@Entity
@Table(name = "T_PREDICTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prediction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "simple_result", nullable = false)
    private String simpleResult;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_simple_result", precision=10, scale=2)
    private BigDecimal pSimpleResult;

    @NotNull
    @Column(name = "double_result", nullable = false)
    private String doubleResult;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_double_result", precision=10, scale=2)
    private BigDecimal pDoubleResult;

    @NotNull
    @Column(name = "simple_half_result", nullable = false)
    private String simpleHalfResult;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_simple_half_result", precision=10, scale=2)
    private BigDecimal pSimpleHalfResult;

    @NotNull
    @Column(name = "double_half_result", nullable = false)
    private String doubleHalfResult;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_double_half_result", precision=10, scale=2)
    private BigDecimal pDoubleHalfResult;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_home_goals", precision=10, scale=2)
    private BigDecimal pHomeGoals;

    @Min(value = 0)
    @Column(name = "away_goals")
    private Integer awayGoals;

    @Min(value = 0)
    @Column(name = "half_home_goals")
    private Integer halfHomeGoals;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_away_goals", precision=10, scale=2)
    private BigDecimal pAwayGoals;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_half_home_goals", precision=10, scale=2)
    private BigDecimal pHalfHomeGoals;

    @Min(value = 0)
    @Column(name = "half_away_goals")
    private Integer halfAwayGoals;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_half_away_goals", precision=10, scale=2)
    private BigDecimal pHalfAwayGoals;

    @Column(name = "more_than25")
    private Boolean moreThan25;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "p_more_than25", precision=10, scale=2)
    private BigDecimal pMoreThan25;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Actor actor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSimpleResult() {
        return simpleResult;
    }

    public void setSimpleResult(String simpleResult) {
        this.simpleResult = simpleResult;
    }

    public BigDecimal getPSimpleResult() {
        return pSimpleResult;
    }

    public void setPSimpleResult(BigDecimal pSimpleResult) {
        this.pSimpleResult = pSimpleResult;
    }

    public String getDoubleResult() {
        return doubleResult;
    }

    public void setDoubleResult(String doubleResult) {
        this.doubleResult = doubleResult;
    }

    public BigDecimal getPDoubleResult() {
        return pDoubleResult;
    }

    public void setPDoubleResult(BigDecimal pDoubleResult) {
        this.pDoubleResult = pDoubleResult;
    }

    public String getSimpleHalfResult() {
        return simpleHalfResult;
    }

    public void setSimpleHalfResult(String simpleHalfResult) {
        this.simpleHalfResult = simpleHalfResult;
    }

    public BigDecimal getPSimpleHalfResult() {
        return pSimpleHalfResult;
    }

    public void setPSimpleHalfResult(BigDecimal pSimpleHalfResult) {
        this.pSimpleHalfResult = pSimpleHalfResult;
    }

    public String getDoubleHalfResult() {
        return doubleHalfResult;
    }

    public void setDoubleHalfResult(String doubleHalfResult) {
        this.doubleHalfResult = doubleHalfResult;
    }

    public BigDecimal getPDoubleHalfResult() {
        return pDoubleHalfResult;
    }

    public void setPDoubleHalfResult(BigDecimal pDoubleHalfResult) {
        this.pDoubleHalfResult = pDoubleHalfResult;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public BigDecimal getPHomeGoals() {
        return pHomeGoals;
    }

    public void setPHomeGoals(BigDecimal pHomeGoals) {
        this.pHomeGoals = pHomeGoals;
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

    public BigDecimal getPAwayGoals() {
        return pAwayGoals;
    }

    public void setPAwayGoals(BigDecimal pAwayGoals) {
        this.pAwayGoals = pAwayGoals;
    }

    public BigDecimal getPHalfHomeGoals() {
        return pHalfHomeGoals;
    }

    public void setPHalfHomeGoals(BigDecimal pHalfHomeGoals) {
        this.pHalfHomeGoals = pHalfHomeGoals;
    }

    public Integer getHalfAwayGoals() {
        return halfAwayGoals;
    }

    public void setHalfAwayGoals(Integer halfAwayGoals) {
        this.halfAwayGoals = halfAwayGoals;
    }

    public BigDecimal getPHalfAwayGoals() {
        return pHalfAwayGoals;
    }

    public void setPHalfAwayGoals(BigDecimal pHalfAwayGoals) {
        this.pHalfAwayGoals = pHalfAwayGoals;
    }

    public Boolean getMoreThan25() {
        return moreThan25;
    }

    public void setMoreThan25(Boolean moreThan25) {
        this.moreThan25 = moreThan25;
    }

    public BigDecimal getPMoreThan25() {
        return pMoreThan25;
    }

    public void setPMoreThan25(BigDecimal pMoreThan25) {
        this.pMoreThan25 = pMoreThan25;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Prediction prediction = (Prediction) o;

        if ( ! Objects.equals(id, prediction.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "id=" + id +
                ", simpleResult='" + simpleResult + "'" +
                ", pSimpleResult='" + pSimpleResult + "'" +
                ", doubleResult='" + doubleResult + "'" +
                ", pDoubleResult='" + pDoubleResult + "'" +
                ", simpleHalfResult='" + simpleHalfResult + "'" +
                ", pSimpleHalfResult='" + pSimpleHalfResult + "'" +
                ", doubleHalfResult='" + doubleHalfResult + "'" +
                ", pDoubleHalfResult='" + pDoubleHalfResult + "'" +
                ", homeGoals='" + homeGoals + "'" +
                ", pHomeGoals='" + pHomeGoals + "'" +
                ", awayGoals='" + awayGoals + "'" +
                ", halfHomeGoals='" + halfHomeGoals + "'" +
                ", pAwayGoals='" + pAwayGoals + "'" +
                ", pHalfHomeGoals='" + pHalfHomeGoals + "'" +
                ", halfAwayGoals='" + halfAwayGoals + "'" +
                ", pHalfAwayGoals='" + pHalfAwayGoals + "'" +
                ", moreThan25='" + moreThan25 + "'" +
                ", pMoreThan25='" + pMoreThan25 + "'" +
                '}';
    }
}
