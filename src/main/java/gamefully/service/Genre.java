package gamefully.service;

import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbTypeAdapter(GenreConverter.class)
public enum Genre
{
    ACTION("Action"),
    ADVENTURE("Adventure"),
    FIGHTING("Fighting"),
    FLIGHT("Flight"),
    HORROR("Horror"),
    MMO("MMO"),
    MUSIC("Music"),
    PARTY("Party"),
    PLATFORM("Platform"),
    PUZZLE("Puzzle"),
    RPG("RPG"),
    RACE("Race"),
    STRATEGY("Strategy"),
    SHOOTER("Shooter"),
    SIMULATION("Simulation"),
    SPORT("Sport"),
    STEALTH("Stealth");

    private final String genreName;

    Genre(final String genre)
    {
        this.genreName = genre;
    }

    @Override
    public String toString() {
        return genreName;
    }

    public static Genre fromCode(String value)
    {
        for (Genre genre :Genre.values()){
            if (genre.toString().equals(value)){
                return genre;
            }
        }
        return null;
    }


}
