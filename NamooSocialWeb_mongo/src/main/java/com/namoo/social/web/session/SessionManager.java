package com.namoo.social.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.namoo.social.domain.User;
import com.namoo.social.service.facade.SocialNetworkService;

public class SessionManager {
	//
	private static final String LOGIN_USER = "loginUser";
	
	private HttpSession session;
	
	//--------------------------------------------------------------------------
	
	private SessionManager(HttpServletRequest request) {
		//
		this.session = request.getSession();
	}
	
	//--------------------------------------------------------------------------
	
	public static SessionManager getInstance(HttpServletRequest request) {
		//
		return new SessionManager(request);
	}
	
	public boolean login(String userId, String password) {
		//
		WebApplicationContext context = 
				WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		
		SocialNetworkService service = context.getBean(SocialNetworkService.class);
		
		if (service.login(userId, password)) {
			User user = service.getUserInfo(userId);
			session.setAttribute(LOGIN_USER, user);
			session.setAttribute("isLogin", true);
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}

	public void logout() {
		//
		session.invalidate();
	}

	public boolean isLogin() {
		//
		return session.getAttribute(LOGIN_USER) != null ? true : false;
	}
	
	public String getLoginId() {
		// 
		if (isLogin()) {
			User user = (User) session.getAttribute(LOGIN_USER);
			return user.getUserId();
		}
		return null;
	}
}
