package apinote.dto;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import apinote.model.Note;

public class NoteDTO {

	String id;

	@NotNull
	String note;
	@NotNull
	int patId;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	Date date;

	public NoteDTO() {
	}

	public NoteDTO(String id, @NotNull String note, @NotNull int patientId, @NotNull Date date) {
		this.id = id;
		this.note = note;
		this.patId = patientId;
		this.date = date;
	}

	public NoteDTO(Note note) {
		this.id = note.getId().toString();
		this.note = note.getNote();
		this.patId = note.getPatientId();
		this.date = note.getDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return Objects.hash(date, id, note, patId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteDTO other = (NoteDTO) obj;
		return Objects.equals(date, other.date) && Objects.equals(id, other.id) && Objects.equals(note, other.note)
				&& patId == other.patId;
	}

	@Override
	public String toString() {
		return "NoteDTO [id=" + id + ", note=" + note + ", patientId=" + patId + ", date=" + date + "]";
	}

}
