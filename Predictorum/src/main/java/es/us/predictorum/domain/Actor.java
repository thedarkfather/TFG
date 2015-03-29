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
 * A Actor.
 */
@Entity
@Table(name = "T_ACTOR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0)
    @Column(name = "sr_points")
    private Integer srPoints;

    @Min(value = 0)
    @Column(name = "dr_points")
    private Integer drPoints;

    @Min(value = 0)
    @Column(name = "s_hrpoints")
    private Integer sHRPoints;

    @Min(value = 0)
    @Column(name = "d_hrpoints")
    private Integer dHRPoints;

    @Min(value = 0)
    @Column(name = "h_gpoints")
    private Integer hGPoints;

    @Min(value = 0)
    @Column(name = "a_gpoints")
    private Integer aGPoints;

    @Min(value = 0)
    @Column(name = "h_hgpoints")
    private Integer hHGPoints;

    @Min(value = 0)
    @Column(name = "h_agpoints")
    private Integer hAGPoints;

    @Min(value = 0)
    @Column(name = "m_t25_points")
    private Integer mT25Points;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Actor> followers = new HashSet<>();

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrPoints() {
        return srPoints;
    }

    public void setSrPoints(Integer srPoints) {
        this.srPoints = srPoints;
    }

    public Integer getDrPoints() {
        return drPoints;
    }

    public void setDrPoints(Integer drPoints) {
        this.drPoints = drPoints;
    }

    public Integer getSHRPoints() {
        return sHRPoints;
    }

    public void setSHRPoints(Integer sHRPoints) {
        this.sHRPoints = sHRPoints;
    }

    public Integer getDHRPoints() {
        return dHRPoints;
    }

    public void setDHRPoints(Integer dHRPoints) {
        this.dHRPoints = dHRPoints;
    }

    public Integer getHGPoints() {
        return hGPoints;
    }

    public void setHGPoints(Integer hGPoints) {
        this.hGPoints = hGPoints;
    }

    public Integer getAGPoints() {
        return aGPoints;
    }

    public void setAGPoints(Integer aGPoints) {
        this.aGPoints = aGPoints;
    }

    public Integer getHHGPoints() {
        return hHGPoints;
    }

    public void setHHGPoints(Integer hHGPoints) {
        this.hHGPoints = hHGPoints;
    }

    public Integer getHAGPoints() {
        return hAGPoints;
    }

    public void setHAGPoints(Integer hAGPoints) {
        this.hAGPoints = hAGPoints;
    }

    public Integer getMT25Points() {
        return mT25Points;
    }

    public void setMT25Points(Integer mT25Points) {
        this.mT25Points = mT25Points;
    }

    public Set<Actor> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Actor> actors) {
        this.followers = actors;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Actor actor = (Actor) o;

        if ( ! Objects.equals(id, actor.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", srPoints='" + srPoints + "'" +
                ", drPoints='" + drPoints + "'" +
                ", sHRPoints='" + sHRPoints + "'" +
                ", dHRPoints='" + dHRPoints + "'" +
                ", hGPoints='" + hGPoints + "'" +
                ", aGPoints='" + aGPoints + "'" +
                ", hHGPoints='" + hHGPoints + "'" +
                ", hAGPoints='" + hAGPoints + "'" +
                ", mT25Points='" + mT25Points + "'" +
                '}';
    }
}
