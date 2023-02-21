package com.tutego.date4u.core.profile;

import com.tutego.date4u.core.profile.unicorn.Unicorn;
import com.tutego.date4u.core.profile.unicorn.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UnicornRepository unicornRepository;

  @Autowired
  public CustomUserDetailsService(UnicornRepository unicornRepository) {
    this.unicornRepository = unicornRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Unicorn unicorn = unicornRepository.findUnicornByEmail(email).get();
    UserDetails userDetails = User.withUsername(unicorn.getEmail())
            .password(unicorn.getPassword())
            .authorities("USER")
            .build();
    return userDetails;
  }
}
