package com.flygo520.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flygo520.service.UsersService;

@Controller
public class UsersController {

	@Resource
	private UsersService usersService;

	@RequestMapping("/getUsers")
	public String getUsers(Model model) {
		model.addAttribute("list", usersService.getUsers());
		return "index.jsp";
	}

}
