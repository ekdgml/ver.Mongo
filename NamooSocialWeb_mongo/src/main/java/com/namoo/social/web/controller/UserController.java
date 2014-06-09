package com.namoo.social.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.social.domain.User;
import com.namoo.social.service.facade.SocialNetworkService;
import com.namoo.social.web.session.LoginRequired;
import com.namoo.social.web.session.SessionManager;
import com.namoo.social.web.util.MessagePage;

@Controller
@RequestMapping("/user")
public class UserController {
	//
	@Autowired
	private SocialNetworkService socialNetworkService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		//
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			HttpServletRequest request,
			@RequestParam("userId") String userId, 
			@RequestParam("password") String password) {
		//
		SessionManager sessionManager = SessionManager.getInstance(request);
		if (sessionManager.login(userId, password)) {
			return new ModelAndView("redirect:/" + userId);
		} else {
			String message = "사용자 정보가 올바르지 않습니다.";
			String linkURL = "user/login"; 

			return MessagePage.information(message, linkURL);
		}
	}
	
	@LoginRequired
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		//
		SessionManager sessionManager = SessionManager.getInstance(request);
		String userId = sessionManager.getLoginId();
		
		sessionManager.logout();
		return "redirect:/" + userId;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		//
		return "user/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, User user) {
		//
		socialNetworkService.registerUser(user);
		
		// 바로 로그인 처리
		SessionManager.getInstance(request).login(user.getUserId(), user.getPassword());
		
		return "redirect:/" + user.getUserId();
	}
	
	@LoginRequired
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public String follow(HttpServletRequest request, 
			@RequestParam("userId") String userId) {
		//
		String loginId = SessionManager.getInstance(request).getLoginId();
		
		socialNetworkService.follow(loginId, userId);
		return "redirect:/users";
	}

	@LoginRequired
	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	public String unfollow(HttpServletRequest request, 
			@RequestParam("userId") String userId) {
		//
		String loginId = SessionManager.getInstance(request).getLoginId();
		
		socialNetworkService.unfollow(loginId, userId);
		return "redirect:/users";
	}
}
