package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.interfaces.rest.profile.ProfileFormData;

import java.time.LocalDate;

public class SignUpFormData extends ProfileFormData {
  private String email;
  private String password;

  public SignUpFormData() {}

  public SignUpFormData(String email, String password, String nickname,
                        LocalDate birthdate, int manelength, int gender,
                        Integer attractedToGender, String description) {
    this.email = email;
    this.password = password;
    setNickname(nickname);
    setBirthdate(birthdate);
    setManelength(manelength);
    setGender(gender);
    setAttractedToGender(attractedToGender);
    setDescription(description);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "SignUpFormData{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            "} " + super.toString();
  }
}
