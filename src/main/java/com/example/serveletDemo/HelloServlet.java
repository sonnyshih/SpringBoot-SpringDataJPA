package com.example.serveletDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "HelloServlet",
        description = "Example Servlet Using Annotations",
        urlPatterns = {"/helloServlet"}
)
public class HelloServlet extends HttpServlet {

    /**
     * Using Get method
     * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello Servlet: doing Getting Method");
    }

    /**
     * Using Post method
     * */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello Servlet: doing Posting Method");

    }
}
