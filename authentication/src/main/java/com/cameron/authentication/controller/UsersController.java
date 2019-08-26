package com.cameron.authentication.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cameron.authentication.models.User;
import com.cameron.authentication.service.UserService;
import com.cameron.authentication.validator.UserValidator;

@Controller
public class UsersController {
	@Autowired
	UserService userService;
	@Autowired
	private UserValidator userValidator;

	@GetMapping("/registration")
	public String registerForm(@ModelAttribute("user") User user) {
		return "users/registrationPage.jsp";
	}

	@GetMapping("/login")
	public String login() {
		return "users/loginPage.jsp";
	}

	// if result has errors, return the registration page (don't worry about
	// validations just now)
	// else, save the user in the database, save the user id in session, and
	// redirect them to the /home route

	@PostMapping(value = "/registration")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "users/registrationPage.jsp";
		}
		User u = userService.registerUser(user);
		session.setAttribute("userId", u.getId());
		return "redirect:/home";
	}

	// if the user is authenticated, save their user id in session
	// else, add error messages and return the login page

	@PostMapping(value = "/login")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		boolean isAuthenticated = userService.authenticateUser(email, password);
		if (isAuthenticated) {
			User u = userService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/home";
		} else {
			model.addAttribute("error", "Invalid login. Try again.");
			return "users/loginPage.jsp";
		}
	}

	// get user from session, save them in the model and return the home page
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);
		return "users/homePage.jsp";
	}

	// invalidate session
	// redirect to login page
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}