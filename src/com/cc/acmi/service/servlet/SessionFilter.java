package com.cc.acmi.service.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cc.acmi.common.User;
import com.epm.acmi.struts.Constants;

/**
 * Http Session Filter - validates if the user is already logged in, if not then forwards the
 * request to login page.
 * @author Jay Hombal
 *
 */
public final class SessionFilter implements Filter {

	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * validates user session and forwards to login page if user has no valid session
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		boolean authorized = false;

		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			String r = req.getRequestURI();

			if (r.equals("/acmicc/logon.do") || r.equals("/acmicc/jsp/login/Login.jsp")) {
				chain.doFilter(request, response);
				return;
			} else {
				HttpSession session = ((HttpServletRequest) request).getSession(false);
				if (session != null) {
					User user = (User) session.getAttribute(Constants.loggedUser);
					if (null != user)
						request.setAttribute("invalidsession", "N");
						authorized = true;
				}
			}

			if (authorized) {
				chain.doFilter(request, response);
				return;
			} else if (filterConfig != null) {
				String login_page = "/jsp/login/Login.jsp"; // filterConfig.getInitParameter("login_page");
				if (login_page != null && !"".equals(login_page)) {
					RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(login_page);
					rd.forward(request, response);
					return;
				}

			}
		}

	}

	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
