package com.namoo.social.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.SocialNetworkService;
import com.namoo.social.web.controller.pres.PresUser;
import com.namoo.social.web.session.LoginRequired;
import com.namoo.social.web.session.SessionManager;
import com.namoo.social.web.util.MessagePage;

@Controller
public class SocialMainController {
	//
	@Autowired
	private SocialNetworkService socialNetworkService;
	
	@RequestMapping("/")
	public String index() {
		//
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String timeline(Model model, 
			@PathVariable("userId") String userId) {
		//                        
		User user = socialNetworkService.getUserDetail(userId);
		
		if (user != null) {
			List<Message> timelineMessages = socialNetworkService.getTimelineMessages(userId);
			
			model.addAttribute("user", user);
			model.addAttribute("messages", timelineMessages);
			return "home";
		} else {
			return "redirect:/user/login";
		}
	}
	
	@LoginRequired
	@RequestMapping(value = "/{userId}/messages", method = RequestMethod.GET)
	public ModelAndView messages(Model model, 
			@PathVariable("userId") String userId) {
		//
		User user = socialNetworkService.getUserDetail(userId);
		
		if (user != null) {
			List<Message> myMessages = socialNetworkService.getMyMessages(userId);
			
			model.addAttribute("user", user);
			model.addAttribute("messages", myMessages);
			return new ModelAndView("home");
		} else {
			String message = "해당 사용자 정보가 없습니다.";
			String linkURL = "/user/login"; 
			return MessagePage.information(message, linkURL);
		}
	}
	
	@LoginRequired
	@RequestMapping(value = "/message/register", method = RequestMethod.POST)
	public String registerMessage(HttpServletRequest request, Message message) {
		//
		String loginId = SessionManager.getInstance(request).getLoginId();
		message.setWriter(new User(loginId));
		
		socialNetworkService.addMessage(message);
		return "redirect:/" + loginId;
	}

	@LoginRequired
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(Model model, HttpServletRequest request) {
		//
		List<User> users = socialNetworkService.getUsers();
		
		String userId = SessionManager.getInstance(request).getLoginId();
		model.addAttribute("users", convert(userId, users));
		
		return "user/users";
	}
	
	//--------------------------------------------------------------------------

	private List<PresUser> convert(String userId, List<User> users) {
		// 
		User loginUser = socialNetworkService.getUserDetail(userId);
		
		List<PresUser> presUsers = new ArrayList<PresUser>();
		for (User user : users) {
			if (user.getUserId().equals(userId)) continue;
			
			PresUser presUser = new PresUser(user);
			if (loginUser.findFollower(user.getUserId()) != null) {
				presUser.setFollowed(true);
			}
			
			if (loginUser.findFollowing(user.getUserId()) != null) {
				presUser.setFollowing(true);
			}
			presUsers.add(presUser);
		}
		return presUsers;
	}
}
