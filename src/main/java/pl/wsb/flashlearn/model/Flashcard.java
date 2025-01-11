package pl.wsb.flashlearn.model;

public class Flashcard {
    private String name;
    private String description;

    public Flashcard() {}

    public Flashcard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
}

