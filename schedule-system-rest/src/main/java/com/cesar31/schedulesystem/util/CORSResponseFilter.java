package com.cesar31.schedulesystem.util;

import org.slf4j.Logger;

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
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CORSResponseFilter implements Filter {

    @Inject
    Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Filtering...");

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "X-Count-Total, Content-Type, Accept, Origin, Authorization");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Expose-Headers", "X-Count-Total, Content-Type, Accept, Origin, Authorization");
        if (((HttpServletResponse) servletResponse).getHeader("Access-Control-Allow-Origin") == null) {
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        }
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (request.getMethod().equals("OPTIONS")) {
            ((HttpServletResponse) servletResponse).addHeader("Content-Type", "application/json");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
