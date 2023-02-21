package com.tutego.date4u.core.profile;

import com.tutego.date4u.core.profile.unicorn.UnicornRepository;
import com.tutego.date4u.interfaces.rest.SearchFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
  private ProfileRepository profileRepository;
  private UnicornRepository unicornRepository;

  @Autowired
  public ProfileService(ProfileRepository profileRepository, UnicornRepository unicornRepository) {
    this.profileRepository = profileRepository;
    this.unicornRepository = unicornRepository;
  }

  public List<Profile> search(SearchFormData searchFormData) {
    LocalDate minBirthdate = LocalDate.now().minusYears(searchFormData.getMaxAge());
    LocalDate maxBirthdate = LocalDate.now().minusYears(searchFormData.getMinAge());

    return profileRepository.search(searchFormData.getMyGender(),
            searchFormData.getAttractedToGender(),
            minBirthdate,
            maxBirthdate,
            searchFormData.getMinManelength(),
            searchFormData.getMaxManelength());
  }

  public List<Profile> getProfiles() {
    return profileRepository.findAll();
  }

  public Optional<Profile> getProfile(long id) {
    return profileRepository.findById(id);
  }

  public Profile getProfile(String email) {
    return unicornRepository.findUnicornByEmail(email).get().getProfile();
  }
}
