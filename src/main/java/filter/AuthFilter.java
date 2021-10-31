package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/.add.html")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("index.html") || uri.endsWith("reg.html")
                || uri.endsWith(".do") || uri.endsWith(".js") || uri.contains("download")) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher("reg.html").forward(req, resp);
            return;
        }
        chain.doFilter(request, response);
    }

}