package gamefully.service.models;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "user")
public class User
{
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    @Column(nullable = true)
    private String phoneNumber;
    private String email;
    private String password;
    @JsonbDateFormat("dd-MM-yyyy HH:mm:ss")
    private LocalDateTime registeredAt;
    private int admin;

    @OneToOne(mappedBy = "user")
    private Wishlist wishlist;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    public User(int id, String firstName, String lastName, String address, String zipCode, String city, String phoneNumber, String email, String password, LocalDateTime registeredAt, int admin)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.registeredAt = registeredAt;
        this.admin = admin;
    }

    public User(){}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public LocalDateTime getRegisteredAt()
    {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt)
    {
        this.registeredAt = registeredAt;
    }

    public int getAdmin()
    {
        return admin;
    }

    public void setAdmin(int admin)
    {
        this.admin = admin;
    }

    public Wishlist getWishlist()
    {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist)
    {
        this.wishlist = wishlist;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }
}
