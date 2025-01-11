package pl.wsb.flashlearn.repository;

import pl.wsb.flashlearn.model.FlashcardSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlashcardSetsRepository extends MongoRepository<FlashcardSet, String> {

    Optional<FlashcardSet> findByTitle(String title);
}
