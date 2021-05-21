package gamefully.service.models;


import gamefully.service.Category;
import gamefully.service.Platform;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "console")
public class Console extends Product
{
    private String brand;
    private String storage;
    private String color;


    public Console(int id, String ean, Category category, Platform platform, String storage, String brand,String title, String description,String color, int purchasePrice, int sellingPrice)
    {
        super(id, ean, category, platform, title, description, purchasePrice, sellingPrice);
        this.color = color;
        this.brand = brand;
        this.storage = storage;
        this.setCategory(Category.CONSOLES);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Console(){}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {return brand; }

    public void setBrand(String brand) {this.brand = brand; }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console product = (Console) o;
        return getId() == product.getId();
    }

}
