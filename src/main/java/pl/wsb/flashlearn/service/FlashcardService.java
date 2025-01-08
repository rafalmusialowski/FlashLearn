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

    public FlashcardSet createFlashcardSet(String title, String description) {
        Optional<FlashcardSet> existingSet = repository.findByTitle(title);
        if (existingSet.isPresent()) {
            throw new IllegalArgumentException("Zbiór o podanej nazwie już istnieje!");
        }

        FlashcardSet newSet = new FlashcardSet();
        newSet.setTitle(title);
        newSet.setDescription(description);

        return repository.save(newSet);
    }

    public void addFlashcardToTopic(String title, Flashcard flashcard) {
        Optional<FlashcardSet> flashcardSetOptional = repository.findByTitle(title);
        if (flashcardSetOptional.isEmpty()) {
            throw new RuntimeException("Topic not found");
        }

        FlashcardSet flashcardSet = flashcardSetOptional.get();
        flashcardSet.getFlashcards().add(flashcard);
        repository.save(flashcardSet);
    }

    public void updateFlashcard(String title, String flashcardName, Flashcard updatedFlashcard) {
        Optional<FlashcardSet> flashcardSetOptional = repository.findByTitle(title);
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
        Optional<FlashcardSet> flashcardSetOptional = repository.findByTitle(title);
        if (flashcardSetOptional.isPresent()) {
            FlashcardSet flashcardSet = flashcardSetOptional.get();
            flashcardSet.getFlashcards().removeIf(flashcard -> flashcard.getName().equals(flashcardName));
            repository.save(flashcardSet);
        } else {
            throw new RuntimeException("Topic not found");
        }
    }
}
