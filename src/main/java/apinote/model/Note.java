package apinote.model;

import java.util.Date;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import apinote.dto.NoteDTO;

public class Note {

	@Id
	ObjectId objectId;

	String note;
	int patId;
	Date date;

	public Note() {
	}

	public Note(NoteDTO noteDTO) {
		if (noteDTO.getId() != null)
			this.objectId = new ObjectId(noteDTO.getId());
		this.note = noteDTO.getNote();
		this.patId = noteDTO.getPatientId();
		this.date = noteDTO.getDate();
	}

	public Note(ObjectId id, String note, int patId, Date date) {
		this.objectId = id;
		this.note = note;
		this.patId = patId;
		this.date = date;
	}

	public ObjectId getId() {
		return objectId;
	}

	public void setId(ObjectId id) {
		this.objectId = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPatientId() {
		return patId;
	}

	public void setPatientId(int patientId) {
		this.patId = patientId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, objectId, note, patId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(date, other.date) && objectId == other.objectId && Objects.equals(note, other.note)
				&& patId == other.patId;
	}

	@Override
	public String toString() {
		return "Note [id=" + objectId + ", note=" + note + ", patientId=" + patId + ", date=" + date + "]";
	}

}
