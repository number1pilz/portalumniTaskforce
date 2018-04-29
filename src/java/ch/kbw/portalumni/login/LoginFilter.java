/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.login;

/**
 *
 * @author Fabian
 */
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/faces/*")
public class LoginFilter implements Filter {

    @Inject
    private LoginSession session;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest myRequest = (HttpServletRequest) request;
        HttpServletResponse myResponse = (HttpServletResponse) response;
        String url = myRequest.getRequestURI();

        System.out.println(toString());
        System.out.println("getRequestURI: " + url);

        if (session.getUser() == null) {
            if ((url.indexOf("events.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");

            } else if ((url.indexOf("fotogalerie.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");

            } else if ((url.indexOf("news.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");
                
            } else if ((url.indexOf("settings.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");

            } else if ((url.indexOf("adminHomepage.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");

            } else if ((url.indexOf("adminUserAdministration.xhtml")) >= 0) {
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/login.xhtml");

            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (url.indexOf("logout.xhtml") >= 0) {
                session.setUser(null);
                myResponse.sendRedirect(myRequest.getServletContext().getContextPath() + "/faces/index.xhtml");

            } else {
                System.out.println("E: chain.doFilter");
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "LoginFilter [getClass()=" + getClass() + "]";
    }

}
