package pl.wsb.flashlearn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sets")
public class FlashcardSet {

    @Id
    private String id;
    private String title;
    private String description;

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
}
