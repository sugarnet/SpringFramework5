package com.dss.schedule.app.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("scheduleInterceptor")
public class ScheduleInterceptor implements HandlerInterceptor {
	
	@Value("${config.schedule.open}")
	private Integer open;
	
	@Value("${config.schedule.close}")
	private Integer close;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hour >= open && hour < close) {
			StringBuilder sb = new StringBuilder();
			sb.append("This is the Client Support. We serve from ");
			sb.append(open);
			sb.append(" to ");
			sb.append(close);
			sb.append(". Thanks for your visit.");
			
			request.setAttribute("message", sb.toString());
			
			return true;
		}
		
		response.sendRedirect(request.getContextPath().concat("/closed"));
		return false;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String message = (String) request.getAttribute("message");
		if (handler instanceof HandlerMethod) {
			modelAndView.addObject("message", message);
		}
		
	}

	
}
