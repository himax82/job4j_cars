package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import model.User;
import store.AdRepostiroty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/post.do")
public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdRepostiroty store = AdRepostiroty.instOf();
        List<Post> list = store.findAllPost();
        ObjectMapper mapper = new ObjectMapper();
        String itemsAsString = mapper.writeValueAsString(list);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(itemsAsString);
    }
}
