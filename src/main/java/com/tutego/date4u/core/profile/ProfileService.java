package com.tutego.date4u.core.profile;

import com.tutego.date4u.core.profile.unicorn.UnicornRepository;
import com.tutego.date4u.interfaces.rest.SearchFormData;
import com.tutego.date4u.interfaces.rest.profile.ProfileFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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

  public void saveProfile(ProfileFormData profileFormData) {
    Optional<Profile> optionalProfile = profileRepository.findById(profileFormData.getId());

    if (optionalProfile.isEmpty()) {
      throw new NoSuchElementException();
    }

    Profile profile = optionalProfile.get();

    if (!profile.getNickname().equals(profileFormData.getNickname())) {
      profile.setNickname(profileFormData.getNickname());
    }

    if (!profile.getBirthdate().equals(profileFormData.getBirthdate())) {
      profile.setBirthdate(profileFormData.getBirthdate());
    }

    if (profile.getManelength() != profileFormData.getManelength()) {
      profile.setManelength(profileFormData.getManelength());
    }

    if (profile.getGender() != profileFormData.getGender()) {
      profile.setGender(profileFormData.getGender());
    }

    if (profile.getAttractedToGender() == null &&
            profileFormData.getAttractedToGender() != null ||
            profile.getAttractedToGender() != null &&
                    profileFormData.getAttractedToGender() != null &&
            profileFormData.getAttractedToGender() != profile.getAttractedToGender()) {
      profile.setAttractedToGender(profileFormData.getAttractedToGender());
    }

    if (profile.getDescription() == null &&
            profileFormData.getDescription() != null ||
            profile.getDescription() != null &&
                    profileFormData.getDescription() != null &&
            !profile.getDescription().equals(profileFormData.getDescription())) {
      profile.setDescription(profileFormData.getDescription());
    }

    profileRepository.save(profile);
  }
}
