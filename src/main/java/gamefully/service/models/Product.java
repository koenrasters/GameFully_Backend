package gamefully.service.models;

import gamefully.service.Category;
import gamefully.service.CategoryConverter;
import gamefully.service.Platform;
import gamefully.service.PlatformConverter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product
{
    @Id
    private int id;
    private String title;
//    @JsonbProperty("purchase price")
    private int purchasePrice;
    private int sellingPrice;
    @Convert(converter = PlatformConverter.class)
    private Platform platform;
    @Convert(converter = CategoryConverter.class)
    private Category category;
    private String description;
    private String ean;
    @JsonbDateFormat("dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonbTransient
    @ManyToMany(mappedBy = "products")
    private List<Wishlist> wishlists;

    @JsonbTransient
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @JsonbTransient
    @OneToMany(mappedBy = "transaction")
    private List<TransactionItem> transactionItems;

    @OneToOne(mappedBy = "product")
    @PrimaryKeyJoinColumn
    private Stock stock;

    public Product(int id, String ean, Category category, Platform platform, String title, String description, int purchasePrice, int sellingPrice)
    {
        this.id = id;
        this.title = title;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.platform = platform;
        this.category = category;
        this.ean = ean;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public Product(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {this.sellingPrice = sellingPrice; }

    public Platform getPlatform() {return platform; }

    public void setPlatform(Platform platform) {this.platform = platform; }

    public Category getCategory() {return category; }

    public void setCategory(Category category) {this.category = category; }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Stock getStock(){ return stock;}

    public void setStock(Stock stock){ this.stock = stock;}

    public List<Wishlist> getWishlists()
    {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists)
    {
        this.wishlists = wishlists;
    }

    public List<TransactionItem> getTransactionItems()
    {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItem> transactionItems)
    {
        this.transactionItems = transactionItems;
    }

    public List<CartItem> getCartItems()
    {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems)
    {
        this.cartItems = cartItems;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId();
    }
}
