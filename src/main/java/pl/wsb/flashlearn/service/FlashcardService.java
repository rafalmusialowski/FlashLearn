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

    public Optional<FlashcardSet> getFlashcardSetById(String id) {
        return repository.findById(id);
    }

    public FlashcardSet saveFlashcardSet(FlashcardSet flashcard) {
        return repository.save(flashcard);
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

    public void updateFlashcard(String title, String flashcardName, Flashcard updatedFlashcard) {
        Optional<FlashcardSet> flashcardSetOptional = getFlashcardSetByTitle(title);
        if (flashcardSetOptional.isPresent()) {
            FlashcardSet flashcardSet = flashcardSetOptional.get();
            for (Flashcard flashcard : flashcardSet.getFlashcards()) {
                if (flashcard.getName().equals(flashcardName)) {
                    flashcard.setName(updatedFlashcard.getName());
                    flashcard.setDescription(updatedFlashcard.getDescription());
                    repository.save(flashcardSet);
                    return;
                }
            }
            throw new RuntimeException("Flashcard not found");
        } else {
            throw new RuntimeException("Topic not found");
        }
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
