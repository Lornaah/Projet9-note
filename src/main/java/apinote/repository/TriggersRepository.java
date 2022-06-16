package apinote.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import apinote.model.Triggers;

public interface TriggersRepository extends MongoRepository<Triggers, ObjectId> {

}
