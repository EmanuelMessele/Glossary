import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Tests the Glossary class methods that don't make the HTML files.
 *
 * @author Emanuel Messele
 */
public class GlossaryTest {

    /**
     * Tests the sortAlphabet method.
     *
     */
    @Test
    public void sortAlphabetfruits() {
        // Test data
        String[] term = { "banana", "apple", "orange", "grape" };
        String[] definition = { "A yellow fruit", "A red fruit",
                "An orange fruit", "A small sweet fruit" };

        Glossary.sortAlphabet(term, definition);

        String[] expectedTerm = { "apple", "banana", "grape", "orange" };
        String[] expectedDefinition = { "A red fruit", "A yellow fruit",
                "A small sweet fruit", "An orange fruit" };

        assertArrayEquals(term, expectedTerm);
        assertArrayEquals(definition, expectedDefinition);

    }

    /**
     * Tests the sortAlphabet transportation.
     *
     */
    @Test
    public void sortAlphabet() {
        // Test data
        String[] term = { "car", "boat", "airplane", "train" };
        String[] definition = { "method of transport for driving on road",
                "method of transport for water", "method of transport for air",
                "method of transport on tracks" };

        Glossary.sortAlphabet(term, definition);

        String[] expectedTerm = { "airplane", "boat", "car", "train" };
        String[] expectedDefinition = { "method of transport for air",
                "method of transport for water",
                "method of transport for driving on road",
                "method of transport on tracks" };

        assertArrayEquals(term, expectedTerm);
        assertArrayEquals(definition, expectedDefinition);

    }

    /**
     * Tests getWordDirectionMatch words, will update the wordExpected map with
     * the given terms and meanings.
     */
    @Test
    public void getWordDirectionMatchwords() {
        // make a random file to write to
        String fileName = "test.txt";
        SimpleWriter file = new SimpleWriter1L(fileName);

        // add words and definitions
        file.println("meaning");
        file.println(
                "something that one wishes to convey, especially by language");
        file.println();
        file.println("term");
        file.println("a word whose definition is in a glossary");
        file.println();
        file.close();

        // wordDefinitions map
        Map<String, String> wordDefinitions = new Map2<>();

        // call method
        Glossary.getWordDirectionMatch(fileName, wordDefinitions);

        // expected map
        Map<String, String> expectedMap = new Map2<>();
        expectedMap.add("meaning",
                "something that one wishes to convey, especially by language");
        expectedMap.add("term", "a word whose definition is in a glossary");

        // assert
        assertEquals(expectedMap, wordDefinitions);
    }

    /**
     * Tests the getWordMatch with the file name inserted and making sure those
     * words and definitions are put into the map.
     */
    @Test
    public void getWordDirectionMatchsports() {
        // make a file to write to
        String fileName = "test2.txt";
        SimpleWriter file = new SimpleWriter1L(fileName);

        // add words and definitions
        file.println("NASCAR");
        file.println("fast cars racing");
        file.println();
        file.println("football");
        file.println("two teams play against each other to score touchdowns");
        file.println();
        file.println("basketball");
        file.println("two teams play against each other to score buckets");
        file.println();
        file.close();

        // wordDefinitions map
        Map<String, String> wordDefinitions = new Map2<>();

        // call the method
        Glossary.getWordDirectionMatch(fileName, wordDefinitions);

        // expectedMap
        Map<String, String> expectedMap = new Map2<>();
        expectedMap.add("NASCAR", "fast cars racing");
        expectedMap.add("football",
                "two teams play against each other to score touchdowns");
        expectedMap.add("basketball",
                "two teams play against each other to score buckets");

        // asset
        assertEquals(expectedMap, wordDefinitions);
    }
}
