package gamefully.service.models;


import gamefully.service.Category;
import gamefully.service.Platform;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "game")
public class Game extends Product
{
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "game_has_genre",
            joinColumns = @JoinColumn(name = "game_ID"),
            inverseJoinColumns = @JoinColumn(name = "genre_ID")
    )
    private List<GenreModel> genres;

    private int pegi;

    public Game(int id, String ean, Category category, Platform platform, int pegi, String title, String description, int purchasePrice, int sellingPrice)
    {
        super(id, ean, category, platform, title, description, purchasePrice, sellingPrice);
        this.pegi = pegi;
        this.setCategory(Category.GAMES);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Game(){}

    public List<GenreModel> getGenres() {return genres; }

    public void setGenres(List<GenreModel> genres) {this.genres = genres; }

    public int getPegi(){ return pegi;}

    public void setPegi(int pegi){ this.pegi = pegi;}

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game product = (Game) o;
        return getId() == product.getId();
    }

    @Override
    public String toString()
    {
        return "Game{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", purchasePrice=" + this.getPurchasePrice() +
                ", sellingPrice=" + this.getSellingPrice() +
                ", platform=" + this.getPlatform() +
                ", category=" + this.getCategory() +
                ", description='" + this.getDescription() + '\'' +
                ", ean='" + this.getEan() + '\'' +
                ", UpdatedAt=" + this.getUpdatedAt() +
                ", wishlists=" + this.getWishlists() +
                ", cartItems=" + this.getCartItems() +
                ", transactionItems=" + this.getTransactionItems() +
                ", stock=" + this.getStock() +
                "genres=" + genres +
                ", pegi=" + pegi +
                '}';
    }
}
