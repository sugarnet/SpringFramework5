package com.dss.form.app.interceptor;

import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ElapsedTimeInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ElapsedTimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		LOGGER.info("ElapsedTimeInterceptor: preHandle() init...");
		
		Long initTime = System.currentTimeMillis();
		request.setAttribute("initTime", initTime);
		
		Random random = new Random();
		Integer delay = random.nextInt(500);
		Thread.sleep(delay);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		Long endTime = System.currentTimeMillis();
		Long initTime = (Long) request.getAttribute("initTime");
		Long elapsedTime = endTime - initTime;
		
		if (Objects.nonNull(modelAndView)) {
			modelAndView.addObject("elapsedTime", elapsedTime);
		}
		
		LOGGER.info("ElapsedTime: %d", elapsedTime);
		
		LOGGER.info("ElapsedTimeInterceptor: postHandle() exiting...");
	}
	
	

}
