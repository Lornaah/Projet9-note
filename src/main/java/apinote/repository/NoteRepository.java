package apinote.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import apinote.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {

	Optional<Note> findByObjectId(ObjectId objId);

	List<Note> findAllByPatId(int patId);

}
