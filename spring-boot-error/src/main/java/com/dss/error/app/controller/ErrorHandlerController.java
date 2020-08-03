package com.dss.error.app.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dss.error.app.exception.UserNotFoundException;

@ControllerAdvice
public class ErrorHandlerController {
	
	@ExceptionHandler(ArithmeticException.class)
	public String arithmeticException(Exception e, Model model) {
		model.addAttribute("error", "Arithmetic Error");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/arithmetic";
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String numberFormatException(Exception e, Model model) {
		model.addAttribute("error", "Format Number Error!!");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/generic-exception-error";
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String userNotFoundException(Exception e, Model model) {
		model.addAttribute("error", "User Not Found!!");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("timestamp", new Date());
		return "error/generic-exception-error";
	}

}
