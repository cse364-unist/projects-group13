package com.example.cse364project.part2.Employee.exception;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
  public EmployeeNotFoundException(String id) {
    super("Could not find employee " + id);
  }
}
