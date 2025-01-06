package pl.wsb.flashlearn.repository;

import pl.wsb.flashlearn.model.FlashcardSet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlashcardSetsRepository extends MongoRepository<FlashcardSet, String> {
}
