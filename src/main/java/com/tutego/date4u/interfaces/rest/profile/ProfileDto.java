package com.tutego.date4u.interfaces.rest.profile;

import com.tutego.date4u.core.profile.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;

record ProfileDto(
    Long id, String nickname, LocalDate birthdate, int manelength, int gender,
    Integer attractedToGender, String description, LocalDateTime lastseen
) {
  static ProfileDto fromDomain( Profile p ) {
    return new ProfileDto(
        p.getId(), p.getNickname(), p.getBirthdate(), p.getManelength(),
        p.getGender(), p.getAttractedToGender(), p.getDescription(),
        p.getLastseen()
    );
  }
}