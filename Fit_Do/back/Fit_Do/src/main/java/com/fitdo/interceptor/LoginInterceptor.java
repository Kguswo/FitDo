package com.fitdo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fitdo.model.dao.UserDao;
import com.fitdo.model.dto.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private final UserDao userDao;

	@Autowired
	public LoginInterceptor(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			String requestedUrl = request.getRequestURL().toString();
			if (!requestedUrl.equals("http://localhost:5173/")) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}
		return true;
	}

}
