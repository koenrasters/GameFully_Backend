package gamefully.service;

import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.AttributeConverter;

public class GenreConverter implements AttributeConverter<Genre, String>, JsonbAdapter<Genre, String>
{
    public String convertToDatabaseColumn(Genre value) {
        if ( value == null ) {
            return null;
        }

        return value.toString();
    }

    public Genre convertToEntityAttribute(String value) {
        if ( value == null ) {
            return null;
        }

        return Genre.fromCode(value);
    }

    @Override
    public String adaptToJson(Genre genre) throws Exception
    {
        return genre.toString();
    }

    @Override
    public Genre adaptFromJson(String s) throws Exception
    {
        return Genre.fromCode(s);
    }
}
