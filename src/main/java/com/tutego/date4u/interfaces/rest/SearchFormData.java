package com.tutego.date4u.interfaces.rest;

public class SearchFormData {
  private short minAge;
  private short maxAge;
  private short minManelength;
  private short maxManelength;
  private byte myGender;
  private Byte attractedToGender;

  public SearchFormData() {
  }

  public SearchFormData(short minAge, short maxAge, short minManelength, short maxManelength, byte myGender, Byte attractedToGender) {
    this.minAge = minAge;
    this.maxAge = maxAge;
    this.minManelength = minManelength;
    this.maxManelength = maxManelength;
    this.myGender = myGender;
    this.attractedToGender = attractedToGender;
  }

  public short getMinAge() {
    return minAge;
  }

  public void setMinAge(short minAge) {
    this.minAge = minAge;
  }

  public short getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(short maxAge) {
    this.maxAge = maxAge;
  }

  public short getMinManelength() {
    return minManelength;
  }

  public void setMinManelength(short minManelength) {
    this.minManelength = minManelength;
  }

  public short getMaxManelength() {
    return maxManelength;
  }

  public void setMaxManelength(short maxManelength) {
    this.maxManelength = maxManelength;
  }

  public byte getMyGender() {
    return myGender;
  }

  public void setMyGender(byte myGender) {
    this.myGender = myGender;
  }

  public Byte getAttractedToGender() {
    return attractedToGender;
  }

  public void setAttractedToGender(Byte attractedToGender) {
    this.attractedToGender = attractedToGender;
  }

  @Override
  public String toString() {
    return "SearchFormData{" +
            "minAge=" + minAge +
            ", maxAge=" + maxAge +
            ", minManelength=" + minManelength +
            ", maxManelength=" + maxManelength +
            ", myGender=" + myGender +
            ", attractedToGender=" + attractedToGender +
            '}';
  }
}
