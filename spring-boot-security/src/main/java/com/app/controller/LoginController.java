package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.entity.User;
import com.app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping(value = { "/", "/login" })
	public String login() {
		return "login";
	}

	@GetMapping(value = "/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping(value = "/registration")
	public String createUser(Model model, @ModelAttribute @Validated User user, BindingResult bindingResult) {
		User userExists = userService.findUserByUserName(user.getUsername());
		if (userExists != null) {
			bindingResult.rejectValue("userName", "error.user", "User already Registred with the usename provided");
		}
		if (bindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.saveUser(user);
			model.addAttribute("success", "User has been registered successfully");
		}
		return "registration";
	}

	@GetMapping(value = "/admin/dashboard")
	public String dashboard(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByUserName(username);
		model.addAttribute("user", user);
		return "admin/home";
	}

}
