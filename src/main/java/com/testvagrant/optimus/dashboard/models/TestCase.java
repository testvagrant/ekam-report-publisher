package com.testvagrant.optimus.dashboard.models;

import java.util.Collection;
import java.util.List;

public class TestCase {
  private String name;
  private String id;
  private Collection<String> sourceTagNames;
  private List<Integer> lines;
  private String status;
  private String uri;
  private String featureFileName;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Collection<String> getSourceTagNames() {
    return sourceTagNames;
  }

  public void setSourceTagNames(Collection<String> sourceTagNames) {
    this.sourceTagNames = sourceTagNames;
  }

  public List<Integer> getLines() {
    return lines;
  }

  public void setLines(List<Integer> lines) {
    this.lines = lines;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getFeatureFileName() {
    return featureFileName;
  }

  public void setFeatureFileName(String featureFileName) {
    this.featureFileName = featureFileName;
  }
}
