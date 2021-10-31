package store;


import model.Body;
import model.Brand;
import model.Post;
import model.User;

import java.util.Collection;
import java.util.List;

public interface Store {

    public Collection<Post> findForDay();

    public User addUser(User user);

    public Collection<Post> findWithPhoto();

    public Post findPostById(int id);

    public User findByEmail(String email);

    public void updateSale(int id);

    public List<Brand> findAllBrand();

    public List<Body> findAllBody();

    public List<Post> findAllPost();

    public Post addPost(Post post);

    public Collection<Post> findByBrand(Brand brand);

}
