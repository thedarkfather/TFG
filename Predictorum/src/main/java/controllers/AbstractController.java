/* AbstractController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public Map<String, Object> panic(Throwable oops) {
		Map<String, Object> result = new HashMap<String, Object>();
		oops.fillInStackTrace();
		result.put("error", oops.toString());
		result.put("trace", oops.getStackTrace());
		return result;
	}

	public Map<String, String> buildErrors(Object form, BindingResult binding) {
		Map<String, String> errors = new HashMap<String, String>();
		List<FieldError> e = binding.getFieldErrors();
		for (FieldError fieldError : e) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errors;
	}

}