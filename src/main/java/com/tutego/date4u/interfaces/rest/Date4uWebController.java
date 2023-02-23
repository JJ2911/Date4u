package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileService;
import com.tutego.date4u.core.profile.unicorn.UnicornService;
import com.tutego.date4u.interfaces.rest.profile.ProfileFormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class Date4uWebController {
  private ProfileService profileService;
  private UnicornService unicornService;
  private final Logger log = LoggerFactory.getLogger(getClass());

  private boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || AnonymousAuthenticationToken.class.
            isAssignableFrom(authentication.getClass())) {
      return false;
    }
    return authentication.isAuthenticated();
  }

  private long getMyId() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return unicornService.getUnicorn(email).getProfile().getId();
  }

  @Autowired
  public Date4uWebController(ProfileService profileService, UnicornService unicornService) {
    this.profileService = profileService;
    this.unicornService = unicornService;
  }

  @RequestMapping("/")
  public String indexPage(Model model) {
    if (isAuthenticated()) {
      model.addAttribute("myId", getMyId());
    }

    return "index";
  }

  @RequestMapping("/profile/{id}")
  public String profilePage(@PathVariable long id, Model model) {
    Optional<Profile> optionalProfile = profileService.getProfile(id);
    if (optionalProfile.isEmpty()) {
      return "redirect:/";
    }

    Profile profile = optionalProfile.get();
    model.addAttribute("myId", getMyId());
    model.addAttribute("photos", profile.getPhotos());
    model.addAttribute("profileFormData", new ProfileFormData(
            profile.getId(), profile.getNickname(), profile.getBirthdate(),
            profile.getManelength(), profile.getGender(),
            profile.getAttractedToGender(), profile.getDescription(),
            profile.getLastseen()
    ));

    return "profile";
  }

  @PostMapping("/save")
  public String saveProfile(@ModelAttribute ProfileFormData profile) {
    log.info(profile.toString());
    profileService.saveProfile(profile);

    return "redirect:/profile/" + profile.getId();
  }

  @RequestMapping("/search")
  public String searchPage(Model model) {
    model.addAttribute("myId", getMyId());
    model.addAttribute("profiles", profileService.getProfiles());
    model.addAttribute("search", new SearchFormData());

    return "search";
  }

  @PostMapping("/search")
  public String searchProfile(@ModelAttribute SearchFormData searchFormData, Model model) {
    log.info(searchFormData.toString());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Profile profile = profileService.getProfile(authentication.getName());
    searchFormData.setMyGender((byte) profile.getGender());
    List<Profile> profiles = profileService.search(searchFormData);

    model.addAttribute("search", searchFormData);
    model.addAttribute("profiles", profiles);
    return "search";
  }

  @RequestMapping("/login")
  public String login(Model model) {
    if (isAuthenticated()) {
      model.addAttribute("myId", getMyId());
      return "redirect:/";
    }

    return "login";
  }
}