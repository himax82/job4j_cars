package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    private String description;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonProperty
    private boolean sale;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL)
    private Brand brand;

    @JsonProperty
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "body_id", nullable = false)
    private Body body;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonProperty
    private boolean photo;

    @JsonProperty
    private int price;

    public static Post of(String description, boolean sale, Brand brand,
                        Body body, User user, int price) {
        Post post = new Post();
        post.setDescription(description);
        post.setCreated(new Date(System.currentTimeMillis()));
        post.setSale(sale);
        post.setBrand(brand);
        post.setBody(body);
        post.setUser(user);
        post.setPrice(price);
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

    public boolean isSale() {
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

    public Body getBody() {
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

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "Post{" + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created + ", sale=" + sale + " + photo =" + photo
                + ", brand=" + brand + ", body=" + body + ", price" + price
                + ", user=" + user + '}';
    }
}