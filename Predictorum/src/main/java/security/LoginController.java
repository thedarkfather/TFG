/* LoginController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package security;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import controllers.AbstractController;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	// @Autowired
	// LoginService service;
	// @Autowired
	// AuthenticationManager authenticationManager;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	// // LoginFailure
	// -----------------------------------------------------------
	//
	@RequestMapping("/error")
	public Map<String, String> failure() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("error", "Invalid credentials");
		return result;
	}

}