package com.imgur.demo.project.spring.h2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Gallery {
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonProperty("data")
  public ArrayList<Data> data;

  public ArrayList<Data> getData() {
    return data;
  }

  public void setData(ArrayList<Data> data) {
    this.data = data;
  }

}
