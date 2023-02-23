package com.tutego.date4u.core.profile.unicorn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnicornService {
  private UnicornRepository unicornRepository;

  @Autowired
  public UnicornService(UnicornRepository unicornRepository) {
    this.unicornRepository = unicornRepository;
  }

  public Unicorn getUnicorn(String email) {
    return unicornRepository.findUnicornByEmail(email).get();
  }
}
