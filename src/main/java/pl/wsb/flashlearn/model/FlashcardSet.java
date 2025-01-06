package pl.wsb.flashlearn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "sets")
public class FlashcardSet {

    @Id
    private String id;
    private String title;
    private String description;
    private List<Flashcard> flashcards = new ArrayList<>();
    public FlashcardSet() {}
    public FlashcardSet(String id, String title, String description) {}
    public FlashcardSet(String title, String description) {}

    // Gettery i Settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
