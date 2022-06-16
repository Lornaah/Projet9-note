package apinote.service;

import java.util.List;

import javax.validation.Valid;

import apinote.dto.NoteDTO;

public interface NoteService {

	List<NoteDTO> getAllNotes(int patientId);

	NoteDTO getNote(String noteId);

	NoteDTO addNote(NoteDTO noteDTO);

	void deleteNote(String id);

	NoteDTO updateNote(@Valid NoteDTO noteDTO);

	int getOccurrences(int patId);

}
