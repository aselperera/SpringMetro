
package com.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team.entity.LoginDTO;
import com.team.entity.User;
import com.team.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginController loginController;
	
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
		
		modelAndView.addObject("loginDetails", new LoginDTO());
		modelAndView.setViewName("index");
		
		return modelAndView;
	}
	
	@RequestMapping("/topUpBalance")
	public ModelAndView getTopUp(@RequestParam("topUp") double topUpAmount) {
		ModelAndView modelAndView = new ModelAndView();
		
		// Get current user
		User currentUser = loginController.getCurrentUser();
		
		String message = null;

		if(userService.topUpBalance(currentUser.getId(), topUpAmount)) {
//			modelAndView.addObject("balance");
			message = "Top up Successful!";
		} else {
			message = "Top Up Failed!";
		}
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("output");
		
		return modelAndView;
	}
	
//	@RequestMapping("/updateUser")
//	public ModelAndView updateUserController(@ModelAttribute("user") User user) {
//		ModelAndView modelAndView=new ModelAndView();
//		
//		User currentUser = loginController.getCurrentUser();
//		String message = null;
//		if(userService.updateUser(currentUser))
//			message="User updated Succesfully";
//		else
//			message="User update Failed";
//		
//		modelAndView.addObject("message", message);
//		modelAndView.setViewName("output");
//		
//		return modelAndView;
//	}
}