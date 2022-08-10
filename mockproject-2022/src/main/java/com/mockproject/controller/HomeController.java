package com.mockproject.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mockproject.constant.SessionConstant;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.ProductsService;
import com.mockproject.service.UsersService;

@Controller
public class HomeController {
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private UsersService usersService;

	
//	@GetMapping("/hello")
//	public String index(Model model) {
//		List<Products> products = productsService.findAll();
//		model.addAttribute("products",products);
//		return "hello";
//	}
//	
//	@GetMapping("/hello2")
//	public String doFindBySlug(Model model) {
//		Products product = productsService.findBySlug("oppo-reno-4");
//		model.addAttribute("product",product);
//		return "hello2";
//	}
	@GetMapping(value = {"/",""})
	public String deGetRedirectIndex() {
		return "redirect:/index";
	}
	
	@GetMapping(value = {"/","/index"," "})
	public String doGetIndex(Model model) {
		List<Products> product = productsService.findAll();
		model.addAttribute("products",product);
		return "user/index";
	}
	
	@GetMapping(value = "/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/login";
	}
	
	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session){
		Users userResponse = usersService.doLogin(userRequest);
		if (ObjectUtils.isNotEmpty(userResponse)) {
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/index";
		}
		return "redirect:/login";
	}
	
	@GetMapping(value = "/logout")
	public String doGetLogout(HttpSession session) {
		session.removeAttribute(SessionConstant.CURRENT_USER);
		return "redirect:/index";
	}	
	
	@GetMapping(value = "/register")
	public String doGetRegister(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/register";
	}
	
	@PostMapping("/register")
	public String doPostRegister(@ModelAttribute("userRequest") Users userRequest, HttpSession session){
		try {
			Users userResponse = usersService.save(userRequest);
			if (ObjectUtils.isNotEmpty(userResponse)) {
				session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
				return "redirect:/index";
			}else {
				return "redirect:/register";		
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
			return "redirect:/register";
		}
	}
	
}
