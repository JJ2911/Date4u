package com.tutego.date4u.interfaces.rest.profile;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProfileFormData {
  private long id;
  private String nickname;
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate birthdate;
  private int manelength;
  private int gender;
  private Integer attractedToGender;
  private String description;
  private LocalDateTime lastseen;

  public ProfileFormData() { }
  public ProfileFormData( long id, String nickname,
                          LocalDate birthdate, int manelength, int gender,
                          Integer attractedToGender, String description, LocalDateTime lastseen ) {
    this.id = id;
    this.nickname = nickname;
    this.birthdate = birthdate;
    this.manelength = manelength;
    this.gender = gender;
    this.attractedToGender = attractedToGender;
    this.description = description;
    this.lastseen = lastseen;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public int getManelength() {
    return manelength;
  }

  public void setManelength(int manelength) {
    this.manelength = manelength;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public Integer getAttractedToGender() {
    return attractedToGender;
  }

  public void setAttractedToGender(Integer attractedToGender) {
    this.attractedToGender = attractedToGender;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getLastseen() {
    return lastseen;
  }

  public void setLastseen(LocalDateTime lastseen) {
    this.lastseen = lastseen;
  }

  @Override
  public String toString() {
    return "ProfileFormData{" +
            "id=" + id +
            ", nickname='" + nickname + '\'' +
            ", birthdate=" + birthdate +
            ", manelength=" + manelength +
            ", gender=" + gender +
            ", attractedToGender=" + attractedToGender +
            ", description='" + description + '\'' +
            ", lastseen=" + lastseen +
            '}';
  }
}