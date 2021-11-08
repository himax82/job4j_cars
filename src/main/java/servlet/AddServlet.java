package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.Config;
import model.Post;
import model.User;
import org.hibernate.HibernateException;
import store.AdRepostiroty;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/add.do")
public class AddServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = AdRepostiroty.instOf();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8));
        Post post = GSON.fromJson(req.getReader(), Post.class);
        post.setUser(user);
        post.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        post.setSale(false);
        File folder =  new File(Config.instOf().getProperty("images"));
        for (File file : folder.listFiles()) {
            if (String.valueOf(user.getId()).equals(file.getName())) {
                post.setPhoto(true);
                break;
            }
        }
        try {
            store.addPost(post);
            writer.print("200");
        } catch (HibernateException e) {
            writer.print("409");
        }
        writer.flush();
    }
}
