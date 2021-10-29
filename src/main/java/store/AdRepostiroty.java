package store;

import model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.Brand;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class AdRepostiroty implements AutoCloseable {

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
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        List<Post> list = new ArrayList<>();
        try {
            list = et(session -> session.createQuery("SELECT a FROM Post a "
                    + "WHERE a.created BETWEEN :date AND current_timestamp ", Post.class)
                    .setParameter("date", c.getTime())
                    .list());
        } catch (Exception e) {
            LOG.error("Don't find post to last day");
        }
        return list;
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
