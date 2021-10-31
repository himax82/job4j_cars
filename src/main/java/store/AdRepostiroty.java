package store;

import model.Body;
import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.Brand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class AdRepostiroty implements Store, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AdRepostiroty.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final AdRepostiroty INST = new AdRepostiroty();
    }

    public static AdRepostiroty instOf() {
        return Lazy.INST;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public Collection<Post> findForDay() {
        List<Post> list = new ArrayList<>();
        try {
            list = et(session -> session.createQuery("SELECT * FROM Post"
                    + " WHERE created >= now() - '1 day'::interval")
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find post to last day");
        }
        return list;
    }

    public User addUser(User user) {
        return et(session -> {
            session.save(user);
            return user;
        });
    }

    public Collection<Post> findWithPhoto() {
        List<Post> list = new ArrayList<>();
        try {
            list = et(session -> session.createQuery("SELECT p FROM Post p "
                    + "WHERE p.photo = true ", Post.class)
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find post with foto");
        }
        return list;
    }

    public Post findPostById(int id) {
        Post post = null;
        try {
            post = et(session -> session.createQuery("SELECT p FROM Post p "
                    + "WHERE p.id = :id", Post.class)
                    .setParameter("id", id)
                    .uniqueResult());
        } catch (Exception e) {
            LOG.error("Don't find Post");
        }
        return post;
    }

    public User findByEmail(String email) {
        User user = null;
        try {
            user = et(session -> session.createQuery("SELECT u FROM User u "
                    + "WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult());
        } catch (Exception e) {
            LOG.error("Don't find email");
        }
        return user;
    }

    public void updateSale(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update model.Post set sale=:sale where id=:id");
        query.setParameter("id", id);
        query.setParameter("sale", true);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public List<Brand> findAllBrand() {
        List<Brand> list = null;
        try {
            list = et(session -> session.createQuery("from model.Brand")
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find All Brand");
        }
        return list;
    }

    public List<Body> findAllBody() {
        List<Body> list = null;
        try {
            list = et(session -> session.createQuery("from model.Body")
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find All Body");
        }
        return list;
    }

    public List<Post> findAllPost() {
        List<Post> list = null;
        try {
            list = et(session -> session.createQuery("from model.Post")
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find All Post");
        }
        return list;
    }

    public Post addPost(Post post) {
        return et(session -> {
            session.save(post);
            return post;
        });
    }

    public Collection<Post> findByBrand(Brand brand) {
        List<Post> list = new ArrayList<>();
        try {
            list = et(session -> session.createQuery("SELECT p FROM Post p "
                    + "WHERE p.brand = :brand", Post.class)
                    .setParameter("brand", brand)
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find post with brand");
        }
        return list;
    }

    private <T> T et(Function<Session, T> f) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T result = f.apply(session);
            tx.commit();
            return result;
        } catch (final Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
