package pl.wsb.flashlearn.service;

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
}
