package com.tutego.date4u.core.profile;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(LikesId.class)
public class Likes {
  @Id
  @ManyToOne
  @JoinColumn(name = "liker_fk")
  private Profile liker;

  @Id
  @ManyToOne
  @JoinColumn(name = "likee_fk")
  private Profile likee;

//  @Id
//  @OneToMany
//  @JoinColumn(name = "likee_fk")
//  private Set<Profile> likee;

  protected Likes(){}

  public Likes(Profile liker, Profile likee){
    this.liker = liker;
    this.likee = likee;
  }

  public Profile getLiker() {
    return liker;
  }

  public Profile getLikee() {
    return likee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Likes likes = (Likes) o;
    return Objects.equals(liker, likes.liker) && Objects.equals(likee, likes.likee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(liker, likee);
  }

  @Override
  public String toString() {
    return "Likes{" +
            "liker=" + liker +
            ", likee=" + likee +
            '}';
  }
}
