package apinote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import apinote.dto.NoteDTO;
import apinote.repository.NoteRepository;
import apinote.service.NoteService;

@SpringBootTest
public class ServiceTest {

	@Autowired
	NoteService noteService;

	@Autowired
	NoteRepository repository;

	@Autowired
	ClearDB clearDB;

	@BeforeEach
	public void clearDB() {
		clearDB.clearDB();
	}

	@Test
	public void getAllNotesTest() {
		NoteDTO firstNote = noteService.addNote(new NoteDTO(null, "test note 1", 0, new Date()));
		NoteDTO secondNote = noteService.addNote(new NoteDTO(null, "test note 2", 0, new Date()));

		List<NoteDTO> list = noteService.getAllNotes(0);

		assertFalse(list.isEmpty());
		assertTrue(list.size() == 2);
	}

	@Test
	public void testGetNoteById() {
		Date date = new Date();
		String content = "test note";
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, content, 0, date));

		NoteDTO note = noteService.getNote(noteToInsert.getId());

		assertNotNull(note);
		assertNotNull(note.getId());
		assertTrue(note.getNote().equals(content));
		assertTrue(note.getPatientId() == 0);
		assertTrue(date.equals(note.getDate()));
	}

	@Test
	public void addNoteTest() {
		Date date = new Date();
		int patientId = 0;
		int oldSize = noteService.getAllNotes(patientId).size();

		NoteDTO noteInserted = noteService.addNote(new NoteDTO(null, "this is the first note !", patientId, date));

		List<NoteDTO> allNotes = noteService.getAllNotes(patientId);

		assertTrue(allNotes.size() == oldSize + 1);
		assertTrue(allNotes.contains(noteInserted));
	}

	@Test
	public void deleteNoteTest() {
		Date date = new Date();
		String content = "test note";
		int patientId = 0;
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, content, patientId, date));

		noteService.deleteNote(noteToInsert.getId());

		List<NoteDTO> allNotes = noteService.getAllNotes(patientId);

		assertTrue(allNotes.isEmpty());

	}

	@Test
	public void getOccurencesTest() {
		Date date = new Date();
		String content = "this note contains ONE occurence : Fumeur";
		int patientId = 0;
		noteService.addNote(new NoteDTO(null, content, patientId, date));

		int occurrences = noteService.getOccurrences(patientId);

		assertTrue(occurrences == 1);
	}
}
