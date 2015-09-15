/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.interceptor;

import com.dp2345.Principal;
import com.dp2345.entity.Shop;
import com.dp2345.entity.Customer;
import com.dp2345.service.ShopService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Interceptor - 商家权限
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public class CustomerInterceptor extends HandlerInterceptorAdapter {

	/** 重定向视图名称前缀 */
	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

	/** "重定向URL"参数名称 */
	private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

	/** "商家"属性名称 */
	private static final String CUSTOMER_ATTRIBUTE_NAME = "customer";

	/** 默认登录URL */
	private static final String DEFAULT_LOGIN_URL = "/customerlogin.jhtml";

	/** 登录URL */
	private String loginUrl = DEFAULT_LOGIN_URL;

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;

	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Customer.PRINCIPAL_ATTRIBUTE_NAME);
		if (principal != null) {
			return true;
		} else {
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				response.addHeader("loginStatus", "accessDenied");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				if (request.getMethod().equalsIgnoreCase("GET")) {
					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
					response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, urlEscapingCharset));
				} else {
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
				return false;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			if (!StringUtils.startsWith(viewName, REDIRECT_VIEW_NAME_PREFIX)) {
				modelAndView.addObject(CUSTOMER_ATTRIBUTE_NAME,shopService.getCurrent());
			}
		}
	}

	/**
	 * 获取登录URL
	 * 
	 * @return 登录URL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置登录URL
	 * 
	 * @param loginUrl
	 *            登录URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}