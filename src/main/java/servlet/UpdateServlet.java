package servlet;

import model.Post;
import model.User;
import store.AdRepostiroty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/update.do")
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8));
        AdRepostiroty store = AdRepostiroty.instOf();
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        Post post = store.findPostById(Integer.parseInt(id));
        if (post.getUser().getId() == user.getId()) {
            store.updateSale(post.getId());
            writer.print("200");
        } else {
            writer.print("407");
        }
        writer.flush();
    }
}
