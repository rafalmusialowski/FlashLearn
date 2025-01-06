package pl.wsb.flashlearn.model;

import org.springframework.data.annotation.Id; // Spring Data MongoDB

public class Flashcard {

    @Id
    private String id; // Odpowiada polu "_id" w MongoDB
    private String question; // Pytanie fiszki
    private String answer;   // Odpowiedź fiszki

    // Gettery i Settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Konstruktor bezargumentowy (wymagany przez frameworki jak Spring)
    public Flashcard() {}

    // Konstruktor dla wygodniejszego tworzenia obiektów
    public Flashcard(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}
