//package com.antontulskih.util;
//
//import com.antontulskih.domain.Customer;
//import com.antontulskih.service.CustomerService;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Set;
//
///**
//* @author Tulskih Anton
//* @{NAME} 30.07.2015
//*/
//@WebFilter(filterName = "AuthenticationFilter")
//public class AuthenticationFilter implements javax.servlet.Filter {
//
//    HttpSession session;
//    CustomerService customerService = new CustomerService();
//    Set<Customer> customerList;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("Filter initialized");
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
//        }
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("Inside doFilter()");
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        String path = req.getRequestURI();
//        System.out.println("requested url is: " + path);
//        session = req.getSession(false);
//        System.out.println("session is " + session);
//        customerList = customerService.getAllSortedById();
//        Integer userId;
//        if (session != null &&
//                (userId = (Integer)session.getAttribute("userId")) != null) {
//            System.out.println("UserId is " + userId);
//            for (Customer c: customerList) {
//                if (c.getId().equals(userId)) {
//                    System.out.println("Match found, chaining request");
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//        }
//        System.out.println("There is no session or authenticated user"
//                + ". Redirecting to sign in page...");
//        req.getRequestDispatcher("/signIn").forward(req, resp);
//        return;
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("Filter destroyed");
//    }
//}
