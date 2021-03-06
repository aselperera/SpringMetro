
package com.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String newUserController(@ModelAttribute("user") User user, RedirectAttributes redirectAttrs) {

		if(userService.saveUser(user))
			redirectAttrs.addFlashAttribute("success", "Sign up was successful!");
		else
			redirectAttrs.addFlashAttribute("error", "Sign up failed!");

		return "redirect:/";
	}
	
	@RequestMapping("/topUpBalance")
	public String getTopUp(@RequestParam("topUp") double topUpAmount, RedirectAttributes redirectAttrs) {
		// Get current user
		User currentUser = loginController.getCurrentUser();
		if(userService.topUpBalance(currentUser.getId(), topUpAmount)) {
			redirectAttrs.addFlashAttribute("success", "Top up was successful!");
		} else {
			redirectAttrs.addFlashAttribute("error", "Top up failed!");
		}
		return "redirect:/swipeInForm";
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