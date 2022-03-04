package com.example.demoapp.Models;

import java.util.ArrayList;

public class People {
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHair_color() {
    return hair_color;
  }

  public void setHair_color(String hair_color) {
    this.hair_color = hair_color;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  private String name;
  private String hair_color;
  private String url;
  private String created;
  private String[] films;

  public String[] getFilms() {
    return films;
  }

  public void setFilms(String[] films) {
    this.films = films;
  }
}
