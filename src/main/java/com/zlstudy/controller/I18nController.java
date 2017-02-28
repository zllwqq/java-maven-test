package com.zlstudy.controller;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class I18nController {
	
	@RequestMapping(value = "/hello")
    public ModelAndView welcome() {
		
		Locale l = LocaleContextHolder.getLocale();
		String locale = LocaleContextHolder.getLocale().getCountry();  
        ModelAndView modelAndView = new ModelAndView("welcome");
 
        return modelAndView;
    }
}
