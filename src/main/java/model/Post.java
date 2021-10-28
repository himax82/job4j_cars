package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private boolean sale;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "body_id", nullable = false)
    private Body body;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Post of(String description, boolean sale, Brand brand,
                        Body body, User user) {
        Post post = new Post();
        post.setDescription(description);
        post.setCreated(new Date(System.currentTimeMillis()));
        post.setSale(sale);
        post.setBrand(brand);
        post.setBody(body);
        post.setUser(user);
        return post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSold() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Body getBodyType() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}