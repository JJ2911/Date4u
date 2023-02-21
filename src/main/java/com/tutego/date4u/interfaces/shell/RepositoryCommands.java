package com.tutego.date4u.interfaces.shell;

import com.tutego.date4u.core.profile.*;
import com.tutego.date4u.core.profile.like.Likes;
import com.tutego.date4u.core.profile.like.LikesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RepositoryCommands {
  private static final int PAGE_SIZE = 10;
  private final ProfileRepository profiles;
  private final LikesRepository likes;
  private Lazy<Page<Profile>> currentPage;

  public RepositoryCommands(ProfileRepository profiles, LikesRepository likes) {
    this.profiles = profiles;
    currentPage = Lazy.of(() -> profiles.findAll(PageRequest.ofSize(PAGE_SIZE)));
    this.likes = likes;
  }

  @ShellMethod("Display all profiles")
  public List<Profile> list() {
    return currentPage.get().getContent();
  }

  @ShellMethod("Set current page to previous page, display the current page")
  List<Profile> pp() {
    currentPage = currentPage.map(page -> profiles.findAll(page.previousOrFirstPageable()));
    return list();
  }

  @ShellMethod("Set current page to next page, display the current page")
  List<Profile> np() {
    currentPage = currentPage.map(page -> profiles.findAll(page.nextOrLastPageable()));
    return list();
  }


  @ShellMethod("Get profiles with manelength greater than or equal")
  List<Profile> demo(short manelength) {
    return profiles.findProfilesByManelengthGreaterThanEqual(manelength);
  }

  @ShellMethod("Display profiles with mane length between a given min and max value")
  public void profilesBetween(short min, short max) {
    profiles.findAll(QProfile.profile.manelength.between(min, max)).forEach(System.out::println);
  }

  @ShellMethod("Display likes of profile")
  public void likesOfProfile(long likerId) {
    Profile liker = profiles.findById(likerId).get();
    likes.findAll(QLikes.likes.liker.eq(liker)).forEach(System.out::println);
  }

  @ShellMethod("Add a like")
  public void addLike(long likerId, long likeeId) {
    Profile liker = profiles.findById(likerId).get();
    Profile likee = profiles.findById(likeeId).get();
    likes.save(new Likes(liker, likee));
  }
}