package pl.wsb.flashlearn.service;

import pl.wsb.flashlearn.model.Flashcard;
import pl.wsb.flashlearn.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    private final FlashcardRepository repository;

    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public List<Flashcard> getAllFlashcards() {
        return repository.findAll();
    }

    public Optional<Flashcard> getFlashcardById(String id) {
        return repository.findById(id);
    }

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return repository.save(flashcard);
    }

    public void deleteFlashcard(String id) {
        repository.deleteById(id);
    }
}
