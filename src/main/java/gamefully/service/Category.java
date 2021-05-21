package gamefully.service;

import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbTypeAdapter(CategoryConverter.class)
public enum Category
{
    CONTROLLERS("Controllers"),
    HEADSETS("Headsets"),
    KEYBOARDS("Keyboards"),
    MICE("Mice"),
    CONSOLES("Consoles"),
    GAMES("Games");

    private final String categoryName;

    Category(final String category)
    {
        this.categoryName = category;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    public static Category fromCode(String value)
    {
        for (Category category :Category.values()){
            if (category.toString().equals(value)){
                return category;
            }
        }
        return null;
    }

}
