package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private ProfileRepository profileRepository;
  private final Logger log = LoggerFactory.getLogger( getClass() );

  public Date4uWebController(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @RequestMapping( "/**" )
  public String indexPage(Model model) {
    List<Profile> profiles = profileRepository.findAll();
    model.addAttribute("totalProfiles", profiles.size());

    return "index";
  }

  @RequestMapping( "/profile/{id}" )
  public String profilePage(@PathVariable long id, Model model) {
    Optional<Profile> optionalProfile = profileRepository.findById(id);
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
    model.addAttribute("profiles", profileRepository.findAll());
    model.addAttribute("search", new SearchFormData());

    return "search";
  }

  @PostMapping( "/search" )
  public String searchProfile( @ModelAttribute SearchFormData search, Model model ) {
    log.info( search.toString() );

    model.addAttribute("search", search);
    return "search";
  }
}