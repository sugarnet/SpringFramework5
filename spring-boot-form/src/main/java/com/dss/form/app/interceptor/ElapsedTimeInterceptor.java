package com.dss.form.app.interceptor;

import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("elapsedTimeInterceptor")
public class ElapsedTimeInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ElapsedTimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			LOGGER.info("Intercepted Method: {}", method.getMethod().getName());
		}
		
		LOGGER.info("ElapsedTimeInterceptor: preHandle() init...");
		LOGGER.info("Intercepting: {}", handler);
		
		Long initTime = System.currentTimeMillis();
		request.setAttribute("initTime", initTime);
		
		Random random = new Random();
		Integer delay = random.nextInt(100);
		Thread.sleep(delay);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (request.getMethod().equalsIgnoreCase("post")) {
			return;
		}
		
		Long endTime = System.currentTimeMillis();
		Long initTime = (Long) request.getAttribute("initTime");
		Long elapsedTime = endTime - initTime;
		
		if (handler instanceof HandlerMethod && Objects.nonNull(modelAndView)) {
			modelAndView.addObject("elapsedTime", elapsedTime);
		}
		
		LOGGER.info("ElapsedTime: {} ms", elapsedTime);
		
		LOGGER.info("ElapsedTimeInterceptor: postHandle() exiting...");
	}
	
	

}
