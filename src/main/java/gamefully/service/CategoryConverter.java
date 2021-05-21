package gamefully.service;

import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category, String>, JsonbAdapter<Category, String>
{
    public String convertToDatabaseColumn(Category value) {
        if ( value == null ) {
            return null;
        }

        return value.toString();
    }

    public Category convertToEntityAttribute(String value) {
        if ( value == null ) {
            return null;
        }

        return Category.fromCode(value);
    }

    @Override
    public String adaptToJson(Category category) throws Exception
    {
        return category.toString();
    }

    @Override
    public Category adaptFromJson(String s) throws Exception
    {
        return Category.fromCode(s);
    }
}
