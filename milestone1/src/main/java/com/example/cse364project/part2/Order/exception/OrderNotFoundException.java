package com.example.cse364project.part2.Order.exception;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
	public OrderNotFoundException(String id) {
		super("Could not find order " + id);
	}
}