package pl.wsb.flashlearn.service;

import pl.wsb.flashlearn.model.Flashcard;
import pl.wsb.flashlearn.model.FlashcardSet;
import pl.wsb.flashlearn.repository.FlashcardSetsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    private final FlashcardSetsRepository repository;

    public FlashcardService(FlashcardSetsRepository repository) {
        this.repository = repository;
    }

    public List<FlashcardSet> getAllFlashcardSets() {
        return repository.findAll();
    }

    public void deleteFlashcard(String id) {
        repository.deleteById(id);
    }

    public Optional<FlashcardSet> getFlashcardSetByTitle(String title) {
        return repository.findByTitle(title);
    }

    public void createFlashcardSet(String title, String description) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("The topic name must not be empty!");
        }

        if (getFlashcardSetByTitle(title).isPresent()) {
            throw new IllegalArgumentException("A collection with the given name already exists!");
        }

        FlashcardSet newSet = new FlashcardSet();
        newSet.setTitle(title);
        newSet.setDescription(description);
        repository.save(newSet);
    }

    public void addFlashcardToTopic(String title, String name, String description) {
        Optional<FlashcardSet> optionalFlashcardSet = getFlashcardSetByTitle(title);
        if (optionalFlashcardSet.isEmpty()) {
            throw new RuntimeException("Topic not found");
        }

        Flashcard newFlashcard = new Flashcard(name, description);
        FlashcardSet flashcardSet = optionalFlashcardSet.get();
        flashcardSet.getFlashcards().add(newFlashcard);
        repository.save(flashcardSet);
    }

    public void deleteFlashcard(String title, String flashcardName) {
        Optional<FlashcardSet> flashcardSetOptional = getFlashcardSetByTitle(title);
        if (flashcardSetOptional.isPresent()) {
            FlashcardSet flashcardSet = flashcardSetOptional.get();
            flashcardSet.getFlashcards().removeIf(flashcard -> flashcard.getName().equals(flashcardName));
            repository.save(flashcardSet);
        } else {
            throw new RuntimeException("Topic not found");
        }
    }
}
