package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileService;
import com.tutego.date4u.interfaces.rest.profile.ProfileFormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final Logger log = LoggerFactory.getLogger( getClass() );

  @Autowired
  public Date4uWebController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @RequestMapping( "/**" )
  public String indexPage(Model model) {
    List<Profile> profiles = profileService.getProfiles();
    model.addAttribute("totalProfiles", profiles.size());

    return "index";
  }

  @RequestMapping( "/profile/{id}" )
  public String profilePage(@PathVariable long id, Model model) {
    Optional<Profile> optionalProfile = profileService.getProfile(id);
    if (optionalProfile.isEmpty()) {
      return "redirect:/";
    }

    Profile profile = optionalProfile.get();
    model.addAttribute("profile", new ProfileFormData(
            profile.getId(), profile.getNickname(), profile.getBirthdate(),
            profile.getManelength(), profile.getGender(),
            profile.getAttractedToGender(), profile.getDescription(),
            profile.getLastseen()
    ));

    return "profile";
  }

  @PostMapping( "/save" )
  public String saveProfile( @ModelAttribute ProfileFormData profile ) {
    log.info( profile.toString() );
    return "redirect:/profile/" + profile.getId();
  }

  @RequestMapping( "/search" )
  public String searchPage(Model model) {
    model.addAttribute("profiles", profileService.getProfiles());
    model.addAttribute("search", new SearchFormData());

    return "search";
  }

  @PostMapping( "/search" )
  public String searchProfile( @ModelAttribute SearchFormData searchFormData, Model model ) {
    log.info( searchFormData.toString() );

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Profile profile = profileService.getProfile(authentication.getName());
    searchFormData.setMyGender((byte) profile.getGender());
    List<Profile> profiles = profileService.search(searchFormData);

    model.addAttribute("search", searchFormData);
    model.addAttribute("profiles", profiles);
    return "search";
  }

  @RequestMapping( "/login" )
  public String login() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return "redirect:/";
    }

    return "login";
  }
}