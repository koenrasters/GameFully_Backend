package gamefully.service.models;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "transaction_item")
public class TransactionItem
{
    @Id
    private int id;
    private int quantity;
    private int total;

    @ManyToOne()
    @JoinColumn(name = "transaction_ID")
    private Transaction transaction;

    @ManyToOne()
    @JoinColumn(name = "product_ID")
    private Product product;

    public TransactionItem(int id, int quantity, int total)
    {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
    }

    public TransactionItem()
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

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }

    public void setTransaction(Transaction transaction)
    {
        this.transaction = transaction;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionItem transactionItem = (TransactionItem) o;
        return getId() == transactionItem.getId();
    }
}
