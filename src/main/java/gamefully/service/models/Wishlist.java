package gamefully.service.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "wishlist")
public class Wishlist
{
    @Id
    private int id;

    @OneToOne()
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_ID"),
            inverseJoinColumns = @JoinColumn(name = "product_ID")
    )
    private List<Product> products;

    public Wishlist(int id)
    {
        this.id = id;
    }

    public Wishlist()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return getId() == wishlist.getId();
    }
}
