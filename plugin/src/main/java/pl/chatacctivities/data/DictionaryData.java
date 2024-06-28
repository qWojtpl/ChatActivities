package pl.chatacctivities.data;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class DictionaryData extends BaseDataHandler {

    private final Set<String> words = new HashSet<>();

    @Override
    public void loadData() throws IOException {
        for(String word : Files.readAllLines(getFile("dictionary.txt").toPath())) {
            addWord(word);
        }
    }

    public void addWord(String word) {
        this.words.add(word);
    }

    public void removeWord(String word) {
        this.words.remove(word);
    }

    public Set<String> getWords() {
        return new HashSet<>(words);
    }

}
