import java.util.ArrayList;
import java.util.List;

public class CharactersDatabase {

    public static Character GOKU;
    public static Character VEGETA;
    private static List<Character> characterList;

    private CharactersDatabase() {
        characterList = new ArrayList<>();

        GOKU = Goku.getInstance();
        characterList.add(GOKU);

        VEGETA = Vegeta.getInstance();
        characterList.add(VEGETA);
    }

    public static void initialize() {
        new CharactersDatabase();
    }

    public static List<Character> getCharacterList() {
        return characterList;
    }

    public static Character getCharacter(String value) {
        for (Character character : characterList)
            if (character.getName().equals(value))
                return character;
        return null;
    }
}
