package gamefully.service.models;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "cart_item")
public class CartItem
{
    @Id
    private int id;
    private int quantity;
    private int total;

    @JsonbTransient
    @ManyToOne()
    @JoinColumn(name = "cart_ID")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "product_ID")
    private Product product;

    public CartItem(int id, int quantity, int total)
    {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
    }

    public CartItem()
    {
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getId() == cartItem.getId();
    }
}
