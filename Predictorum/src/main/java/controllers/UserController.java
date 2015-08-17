package controllers;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import responses.GeneralResponse;
import security.LoginService;
import services.UserService;
import domain.User;
import forms.EditImageUserForm;
import forms.EditUserForm;
import forms.FollowUserForm;
import forms.UserDetailsForm;
import forms.UserToList;
import forms.UserToRank;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	// quitar luego
	@Autowired
	private LoginService loginService;

	@RequestMapping(method = RequestMethod.GET, value = "/ranking")
	public List<UserToRank> findRanking() {
		List<UserToRank> usersToList = userService.findRankedUsers();
		return usersToList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listFollowing")
	public Collection<UserToList> findFollowing() {
		Collection<User> userAux = userService.findFollowing();
		Collection<UserToList> usersToList = userService
				.reconstructsToList(userAux);
		return usersToList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listFollowers")
	public Collection<UserToList> findFollowers() {
		Collection<User> userAux = userService.findFollowers();
		Collection<UserToList> usersToList = userService
				.reconstructsToList(userAux);
		return usersToList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find/{cadena}")
	public Collection<UserToList> findUserByString(@PathVariable String cadena) {
		Collection<User> userAux = userService.findUserByString(cadena);
		Collection<UserToList> usersToList = userService
				.reconstructsToList(userAux);
		return usersToList;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse follow(
			@RequestBody @Valid FollowUserForm followUserForm,
			BindingResult binding) {
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(
					followUserForm, binding));
		} else {
			try {
				userService.followUser(followUserForm.getUserId());
				generalResponse = new GeneralResponse(true,
						new HashMap<String, String>());
			} catch (Throwable oops) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/profile")
	public UserDetailsForm profile() {
		// Al ser null la llamada al getProfile te devolvera el perfil del
		// principal
		UserDetailsForm userDetailsForm = userService.getProfile(null);
		return userDetailsForm;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/display/{userId}")
	public UserDetailsForm display(@PathVariable Integer userId) {
		UserDetailsForm userDetailsForm = userService.getProfile(userId);
		return userDetailsForm;
	}

	@RequestMapping(value = "/changeImage", method = RequestMethod.POST)
	public GeneralResponse editUserImage(HttpServletRequest request, HttpServletResponse response) {
		GeneralResponse generalResponse = null;
		MultipartHttpServletRequest mRequest;
		// Miraremos si tiene error
		Boolean tieneFoto = false;
		byte[] image = null;

		EditImageUserForm editImageUserForm = new EditImageUserForm();
		editImageUserForm.setImage(image);
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				tieneFoto = true;
				MultipartFile mFile = mRequest.getFile(itr.next());

				if (mFile.getSize() <= 2097152) {
					// Comprobamos que sea una imagen
					String cadena = mFile.getOriginalFilename();
					Boolean formatoCorrecto = checkFormato(cadena);
					if (formatoCorrecto) {

						image = mFile.getBytes(); // así pasamos de
													// MultipartFile a byte[]
						editImageUserForm.setImage(image);
						User user = userService.reconstructToChangeImage(editImageUserForm);
						userService.save(user);
						generalResponse = new GeneralResponse(true, null);

					} else {
						// no tiene el formato correcto
						Map<String, String> errors = new HashMap<String, String>();
						errors.put("format","The image must be jpg, jpeg, gif or png.");
						generalResponse = new GeneralResponse(false, errors);
					}
				} else {
					// Supera el tamaño permitido
					Map<String, String> errors = new HashMap<String, String>();
					errors.put("tam", "The maximum size is 2Mb.");
					generalResponse = new GeneralResponse(false, errors);
				}
				break; // solo tendremos una imagen
			}

			if (!tieneFoto) {
				URL url = new URL("http://www.indre-reisid.ee/wp-content/themes/envision/lib/images/default-placeholder.png");
				InputStream is = url.openStream();
				image = IOUtils.toByteArray(is);
				editImageUserForm.setImage(image);
				User user = userService.reconstructToChangeImage(editImageUserForm);
				userService.save(user);
				generalResponse = new GeneralResponse(true, null);
			}
		} catch (Exception e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("notCommit", "You can not commit this operation.");
			generalResponse = new GeneralResponse(false, errors);
		}
		return generalResponse;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse editUser(@RequestBody @Valid EditUserForm editUserForm, BindingResult binding) {
		GeneralResponse generalResponse;
		if (binding.hasErrors()) {
			generalResponse = new GeneralResponse(false, buildErrors(editUserForm, binding));
		} else {
			try {
				if(!userService.wrongChangePassword(editUserForm)){
					//password y repeat password son distintas
					Map<String, String> errors = new HashMap<String, String>();
					errors.put("fail", "Passwords must be equals");
					generalResponse = new GeneralResponse(false, errors);
				}else{
					userService.editUser(editUserForm);
					generalResponse = new GeneralResponse(true,	new HashMap<String, String>());
				}
				
			} catch (Throwable oops) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("fail", "You can not commit this operation");
				generalResponse = new GeneralResponse(false, errors);
			}
		}
		return generalResponse;
	}
	
	public boolean checkFormato(String cadena) {
		Boolean res = false;
		String cad = cadena.toLowerCase();
		Pattern pat = Pattern.compile("^.*(?:jpe?g|gif|png)$");
		Matcher mat = pat.matcher(cad);
		if (mat.matches()) {
			res = true;
		}
		return res;
	}


}
