package com.tutego.date4u.core.profile.like;

import com.tutego.date4u.core.profile.Profile;

import java.io.Serializable;
import java.util.Objects;

public class LikesId implements Serializable {
  private Profile liker;
  private Profile likee;

  protected LikesId(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LikesId likesId = (LikesId) o;
    return Objects.equals(liker, likesId.liker) && Objects.equals(likee, likesId.likee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(liker, likee);
  }
}

