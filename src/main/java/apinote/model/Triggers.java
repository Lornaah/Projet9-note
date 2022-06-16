package apinote.model;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Triggers {

	@Id
	ObjectId objectId;

	String word;

	public Triggers() {
	}

	public Triggers(ObjectId objectId, String word) {
		this.objectId = objectId;
		this.word = word;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectId, word);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triggers other = (Triggers) obj;
		return Objects.equals(objectId, other.objectId) && Objects.equals(word, other.word);
	}

	@Override
	public String toString() {
		return "Triggers [objectId=" + objectId + ", word=" + word + "]";
	}

}
