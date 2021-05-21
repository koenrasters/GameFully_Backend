package gamefully.service.models;

import gamefully.service.Category;
import gamefully.service.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "accessory")
public class Accessory extends Product
{
    private String brand;
    private String color;

    public Accessory(int id,String ean, Category category, Platform platform, String brand,String title,String description, String color, int purchasePrice, int sellingPrice)
    {
        super(id, ean, category, platform, title, description, purchasePrice, sellingPrice);
        this.brand = brand;
        this.color = color;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Accessory(){}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {return brand; }

    public void setBrand(String brand) {this.brand = brand; }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accessory product = (Accessory) o;
        return getId() == product.getId();
    }

}
