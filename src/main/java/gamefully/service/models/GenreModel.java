package gamefully.service.models;

import gamefully.service.Genre;
import gamefully.service.GenreConverter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "genre")
public class GenreModel
{
    @Id
    private int id;

    @Convert(converter = GenreConverter.class)
    private Genre title;

    @JsonbTransient
    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Game> games;

    public GenreModel(int id, Genre title)
    {
        this.id = id;
        this.title = title;
    }

    public GenreModel(){}

    public int getId(){ return id;}

    public void setId(int id){ this.id = id;}

    public Genre getTitle(){ return title;}

    public void setTitle(Genre title){ this.title = title;}

    public List<Game> getGames(){ return games;}

    public void setGames(List<Game> games){ this.games = games;}

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreModel genreModel = (GenreModel) o;
        return getId() == genreModel.getId();
    }


}
