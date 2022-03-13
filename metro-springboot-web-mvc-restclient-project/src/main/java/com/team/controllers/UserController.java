
package com.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.team.entity.User;
import com.team.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/signup")
	public ModelAndView newUserPageController() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("signUpUser");
		
		return modelAndView;
	}
	
	@RequestMapping("/saveUser")
	public ModelAndView newUserController(@ModelAttribute("user") User user) {
		ModelAndView modelAndView=new ModelAndView();
		
		String message = null;
		if(userService.saveUser(user))
			message="User Added Succesfully";
		else
			message="User Addition Failed";
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("output");
		
		return modelAndView;
	}
}