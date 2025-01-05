package pl.wsb.flashlearn.repository;

import pl.wsb.flashlearn.model.Flashcard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlashcardRepository extends MongoRepository<Flashcard, String> {
}
