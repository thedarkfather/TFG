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
 * A Comment.
 */
@Entity
@Table(name = "T_COMMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Min(value = 0)
    @Column(name = "pos_points")
    private Integer posPoints;

    @Column(name = "neg_points")
    private Integer negPoints;

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> childs = new HashSet<>();

    @ManyToOne
    private Prediction prediction;

    @ManyToOne
    private Actor actor;

    @ManyToOne
    private Comment comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosPoints() {
        return posPoints;
    }

    public void setPosPoints(Integer posPoints) {
        this.posPoints = posPoints;
    }

    public Integer getNegPoints() {
        return negPoints;
    }

    public void setNegPoints(Integer negPoints) {
        this.negPoints = negPoints;
    }

    public Set<Comment> getChilds() {
        return childs;
    }

    public void setChilds(Set<Comment> comments) {
        this.childs = comments;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Comment comment = (Comment) o;

        if ( ! Objects.equals(id, comment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + "'" +
                ", posPoints='" + posPoints + "'" +
                ", negPoints='" + negPoints + "'" +
                '}';
    }
}
