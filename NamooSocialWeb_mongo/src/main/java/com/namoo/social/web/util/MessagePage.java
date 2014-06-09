package com.namoo.social.web.util;

import org.springframework.web.servlet.ModelAndView;

public class MessagePage {
	//
	public static ModelAndView information(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("popup/info");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView error(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("popup/error");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView confirm(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("popup/confirm");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView confirm(String message, String confirmURL, String cancelURL) {
		//
		ModelAndView mav = new ModelAndView("popup/confirm");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		mav.addObject("cancelURL", cancelURL);
		
		return mav;
	}
}
