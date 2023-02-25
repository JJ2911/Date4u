package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.photo.Photo;
import com.tutego.date4u.core.photo.PhotoService;
import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileService;
import com.tutego.date4u.core.profile.unicorn.Unicorn;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class Date4uWebController {
  private PhotoService photoService;
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
  public Date4uWebController(ProfileService profileService, UnicornService unicornService, PhotoService photoService) {
    this.profileService = profileService;
    this.unicornService = unicornService;
    this.photoService = photoService;
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
  public String saveProfile(@ModelAttribute ProfileFormData profile, RedirectAttributes redirectAttributes) {
    log.info(profile.toString());
    profileService.saveProfile(profile);
    redirectAttributes.addFlashAttribute("message", "You successfully updated your profile.");

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
  public String login() {
    if (isAuthenticated()) {
      return "redirect:/";
    }

    return "login";
  }

  @RequestMapping("/signup")
  public String signup(Model model) {
    if (isAuthenticated()) {
      return "redirect:/";
    }

    model.addAttribute("signUpFormData", new SignUpFormData());
    model.addAttribute("max", LocalDate.now().minusYears(18));
    return "signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute SignUpFormData signUpFormData,
                       @RequestParam("file") MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {
    Profile profile = new Profile(
            signUpFormData.getNickname(),
            signUpFormData.getBirthdate(),
            signUpFormData.getManelength(),
            signUpFormData.getGender(),
            signUpFormData.getAttractedToGender(),
            signUpFormData.getDescription(),
            LocalDateTime.now());

    Unicorn unicorn = new Unicorn(
            signUpFormData.getEmail(),
            signUpFormData.getPassword(),
            profile);

    profile.setUnicorn(unicorn);

    String imageName = photoService.upload(file.getBytes());
    Photo photo = new Photo(profile, imageName, true, LocalDateTime.now());
    profile.add(photo);

    unicornService.createUnicorn(unicorn);
    redirectAttributes.addFlashAttribute("message", "You successfully created your profile.");

    return "redirect:/login";
  }
}