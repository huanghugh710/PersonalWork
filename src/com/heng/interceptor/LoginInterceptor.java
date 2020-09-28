package com.heng.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.heng.domain.User;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//获取请求路径
		String servletPath = request.getServletPath();
		StringBuffer requestURL = request.getRequestURL();
		System.out.println(servletPath);
		System.out.println(requestURL);
		
		//判断是否有用户登录
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("session_user");
		if(user!=null){
			System.out.println(user.getName());
			return true;
		}else{
			if(servletPath.contains("/order.action")||servletPath.contains("/saveOrder.action")||servletPath.contains("/orderList.action")){
				response.sendRedirect(request.getContextPath()+"/tologin");
				
				//返回值true：继续执行请求
				//返回值false：拦截当前请求
				return false;
				
			}else{
				return true;
			}
			
			
			
			
		}
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

}
