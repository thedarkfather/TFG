package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.User;
import forms.JoinForm;
import responses.GeneralResponse;
import services.UserService;

@Controller
public class JoinControllers extends AbstractController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/joing", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse join(@RequestBody @Valid JoinForm joinForm, BindingResult binding){
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(joinForm, binding));
		} else {
			try{
				Boolean success = true;
				Map<String,String> errors = new HashMap<String, String>();
				generalResponse = new GeneralResponse(success, errors);
				if(userService.checkPassword(joinForm)){
					errors.put("passord", "Password and his confirmation are differents.");
					success = false;
				}
				if(userService.checkUniqueUsername(joinForm)){
					errors.put("notUnique", "There's other user with the same user name.");
					success = false;
				}
				if(success){
					User user = userService.reconstructJoin(joinForm);
					userService.save(user);					
				}
				generalResponse = new GeneralResponse(success, errors);			
			}catch (Throwable oops){
				Map<String,String> errors = new HashMap<String,String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}

}
