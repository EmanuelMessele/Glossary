import components.map.Map;
import components.map.Map2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program will take in a file of words and definitions and using mapping,
 * create a glossary.
 *
 * @author Emanuel Messele
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Inputs a word and its definition from the given file and stores them in
     * the given {@code Map}.
     *
     * @param fileName
     *            the name of the input file
     * @param wordDefinitions
     *            the word -> definitions
     * @replaces definitions
     * @requires <pre>
     *      required word on one line, definition after with , entire file needs to have
     *      one extra new line
     * </pre>
     * @ensures [definition contains word -> definition from file fileName]
     */
    public static void getWordDirectionMatch(String fileName,
            Map<String, String> wordDefinitions) {
        assert fileName != null : "Violation of: fileName is not null";
        assert wordDefinitions != null : "Violation of: priceMap is not null";

        SimpleReader in = new SimpleReader1L(fileName);
        SimpleWriter out = new SimpleWriter1L();

        while (!in.atEOS()) {

            // read the word first line
            String word = in.nextLine(); // remove white space

            String definition = in.nextLine(); // remove white space

            String temp = "";
            while (!definition.equals("")) {
                temp += definition;
                definition = in.nextLine();
            }

            // add into the map
            wordDefinitions.add(word, temp);
        }

        out.close();
        in.close();
    }

    /**
     * Given the arrays filled with the terms and definitions, sort the terms
     * alphabetically and based on the terms, match the definitions with the
     * terms in which they are indexed.
     *
     * @param word
     *            array of words
     * @param definitions
     *            array of definitions
     * @updates word and definitions
     */
    public static void sortAlphabet(String[] word, String[] definitions) {
        assert word != null : "Violation of: word is not null";
        assert definitions != null : "Violation of: word is not null";

        for (int i = 0; i < word.length - 1; i++) {

            for (int j = i + 1; j < word.length; j++) {

                if (word[i].compareTo(word[j]) > 0) {
                    // Swap words
                    String tempWord = word[i];
                    word[i] = word[j];
                    word[j] = tempWord;

                    // Swap definitions accordingly
                    String tempDefinition = definitions[i];
                    definitions[i] = definitions[j];
                    definitions[j] = tempDefinition;
                }
            }
        }
    }

    /**
     * Given the term, its definition, the list of terms, and the path to the
     * folder where files will be saved, the program will make an HTML file with
     * the word as the title and the definition as the body and it will also
     * check the definitions for terms within its list to link back to those
     * specific words.
     *
     * @param term
     *            term
     * @param definition
     *            definition of the term
     * @param terms
     *            list of terms
     * @param folder
     *            the path to the folder where HTML files will be saved
     */
    public static void generateHTMLFile(String term, String definition,
            String[] terms, String folder) {
        assert term != null : "Violation of: term is not null";
        assert definition != null : "Violation of: definition is not null";
        assert terms != null : "Violation of: terms is not null";
        assert folder != null : "Violation of: folderPath is not null";

        // write to the specific file by taking folder name for path
        String fileName = folder + "/" + term + ".html";
        SimpleWriter file = new SimpleWriter1L(fileName);

        file.println("<html>");
        file.println("<head><title>" + term + "</title></head>");
        file.println("<body>");

        // italicizes and makes term red
        file.println("<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>");

        // split definition into words and put in arrays
        String[] wordsInDefinition = definition.split("\\s+");

        // for each loop
        for (String word : wordsInDefinition) {
            // check if the word is one of the terms
            boolean isTerm = false;
            for (String t : terms) {
                if (t.equals(word)) {
                    isTerm = true;
                }
            }
            // if the word is a term, make it an HTML file
            if (isTerm) {
                file.print("<a href=\"" + word + ".html\">" + word + "</a> ");

                // if not just print the word back
            } else {
                file.print(word + " ");
            }
        }

        // i believe this makes the line. it was contained in the page source in the index
        file.println("<hr />");
        file.println("<p>Return to <a href=\"index.html\">index</a>.</p>");

        file.println("</body>");
        file.println("</html>");

        file.close();
    }

    /**
     * Given the terms, the String of the output folder where files will be
     * saved, and simpleWriter file to write to, create an HTML file for each
     * term with term and definition.
     *
     * @param word
     *            word array
     * @param definitions
     *            definition array
     * @param folderName
     *            the name of the folder where HTML files will be saved
     */
    public static void generateHTMLFiles(String[] word, String[] definitions,
            String folderName) {
        assert word != null : "Violation of: word is not null";
        assert definitions != null : "Violation of: definitions is not null";
        assert folderName != null : "Violation of: folderPath is not null";

        // iterate thru the array of words and make a file for them all
        for (int i = 0; i < word.length; i++) {
            generateHTMLFile(word[i], definitions[i], word, folderName);
        }
    }

    /**
     * Given the terms, the name of the output folder, and writes the index.html
     * page.
     *
     * @param word
     *            word array
     * @param folder
     *            the name of the folder where HTML files will be saved
     */
    public static void generateIndexHTML(String[] word, String folder) {
        assert word != null : "Violation of: word is not null";
        assert folder != null : "Violation of: folderPath is not null";

        String indexFilePath = folder + "/index.html";
        SimpleWriter indexFile = new SimpleWriter1L(indexFilePath);

        // code for html
        indexFile.println("<html>");
        indexFile.println("<head><title>Glossary</title></head>");
        indexFile.println("<body>");

        indexFile.println("<h1>Glossary</h1>");
        indexFile.println("<h2>Index</h2>");

        // iterate through the list of words
        indexFile.println("<ul>");
        for (String term : word) {
            indexFile.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }
        indexFile.println("</ul>");

        indexFile.println("</body>");
        indexFile.println("</html>");

        indexFile.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // file name
        out.print("Insert File Name: ");
        String fileName = in.nextLine();

        // output folder name
        out.print("Name The Folder You Want The File Saved In:  ");
        String folderPath = in.nextLine();

        // map for our terms and definitions
        Map<String, String> wordDefinitions = new Map2<>();

        // fill map with all words and definitions
        getWordDirectionMatch(fileName, wordDefinitions);

        // arrays for the terms and definitions
        String[] terms = new String[wordDefinitions.size()];
        String[] definitions = new String[wordDefinitions.size()];

        // remove any loop
        int i = 0;
        while (wordDefinitions.size() > 0) {
            Map.Pair<String, String> temp = wordDefinitions.removeAny();

            // take out words and put key into terms array and key value into def array
            terms[i] = temp.key();
            definitions[i] = temp.value();

            // iterate
            i++;
        }

        // call sort definitions method
        sortAlphabet(terms, definitions);

        // create index.html file
        generateIndexHTML(terms, folderPath);

        // creating the files using arrays as parameters
        generateHTMLFiles(terms, definitions, folderPath);

        in.close();
        out.close();
    }

}
