package gamefully.service.models;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
public class Transaction
{
    @Id
    private int id;
    private int total;
    @JsonbDateFormat("dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionDate;

    @ManyToOne()
    @JoinColumn(name = "user_ID")
    private User user;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionItem> transactionItems;

    public Transaction(int id, int total, LocalDateTime transactionDate)
    {
        this.id = id;
        this.total = total;
        this.transactionDate = transactionDate;
    }

    public Transaction()
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

    public LocalDateTime getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<TransactionItem> getTransactionItems()
    {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItem> transactionItems)
    {
        this.transactionItems = transactionItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return getId() == transaction.getId();
    }
}
