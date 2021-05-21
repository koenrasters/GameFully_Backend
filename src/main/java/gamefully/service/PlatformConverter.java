package gamefully.service;

import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.AttributeConverter;

public class PlatformConverter implements AttributeConverter<Platform, String>, JsonbAdapter<Platform, String>
{
    public String convertToDatabaseColumn(Platform value) {
        if ( value == null ) {
            return null;
        }

        return value.toString();
    }

    public Platform convertToEntityAttribute(String value) {
        if ( value == null ) {
            return null;
        }

        return Platform.fromCode(value);
    }

    @Override
    public String adaptToJson(Platform platform) throws Exception
    {
        return platform.toString();
    }

    @Override
    public Platform adaptFromJson(String string) throws Exception
    {
        return Platform.fromCode(string);
    }
}
