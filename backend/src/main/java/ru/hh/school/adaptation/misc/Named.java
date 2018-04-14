package ru.hh.school.adaptation.misc;

public class Named<T> {

  private String name;
  private T object;

  public Named(String name, T object) {
    this.name = name;
    this.object = object;
  }

  public String name() {
    return name;
  }

  public T get() {
    return object;
  }
}
