package gamefully.service.models;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "stock")
public class Stock
{
    @Id
    private int id;
    private int quantity;
    @JsonbDateFormat("dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonbTransient
    @OneToOne()
    @MapsId
    @JoinColumn(name = "ID")
    private Product product;

    public Stock(int id, int quantity, LocalDateTime updatedAt)
    {
        this.id = id;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
    }

    public Stock(){}

    public int getId(){ return id;}

    public void setId(int id){ this.id = id;}

    public int getQuantity(){ return quantity;}

    public void setQuantity(int quantity){this.quantity = quantity;}

    public LocalDateTime getUpdatedAt(){ return updatedAt;}

    public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt = updatedAt;}

    public Product getProduct(){ return product;}

    public void setProduct(Product product){ this.product = product;}

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return getId() == stock.getId();
    }
}
