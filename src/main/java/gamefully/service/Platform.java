package gamefully.service;

import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbTypeAdapter(PlatformConverter.class)
public enum Platform
{
    PS4("PS4"),
    PS5("PS5"),
    XBOX_ONE_X("XBOX One X"),
    XBOX_ONE_S("XBOX One S"),
    XBOX_SERIES_X("XBOX Series X"),
    SWITCH("Switch"),
    SWITCH_LITE("Switch Light"),
    PC("PC");

    private final String platformName;

    Platform(final String platform)
    {
        this.platformName = platform;
    }

    @Override
    public String toString() {
        return platformName;
    }

    public static Platform fromCode(String value)
    {
        for (Platform platform :Platform.values()){
            if (platform.toString().equals(value)){
                return platform;
            }
        }
        return null;
    }

}
