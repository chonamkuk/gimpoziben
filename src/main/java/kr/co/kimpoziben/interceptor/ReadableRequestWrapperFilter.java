package kr.co.kimpoziben.interceptor;

import javax.servlet.*;
import java.io.IOException;

public class ReadableRequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //System.out.println("필터 호출 됨");
//        ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest)request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}