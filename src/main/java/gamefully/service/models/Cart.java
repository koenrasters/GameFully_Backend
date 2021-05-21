package gamefully.service.models;


import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "cart")
public class Cart
{
    @Id
    private int id;
    private int total;

    @JsonbTransient
    @OneToOne()
    @JoinColumn(name = "user_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    public Cart(int id, int total)
    {
        this.id = id;
        this.total = total;
    }

    public Cart()
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

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public List<CartItem> getCartItems()
    {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems)
    {
        this.cartItems = cartItems;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getId() == cart.getId();
    }
}
