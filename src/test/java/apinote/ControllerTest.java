package apinote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import apinote.dto.NoteDTO;
import apinote.service.NoteService;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	NoteService noteService;

	@Autowired
	ClearDB clearDB;

	@BeforeEach
	public void clearDB() {
		clearDB.clearDB();
	}

	@Test
	public void testGetAllNotes() throws Exception {
		NoteDTO firstNote = noteService.addNote(new NoteDTO(null, "test note 1", 0, new Date()));
		NoteDTO secondNote = noteService.addNote(new NoteDTO(null, "test note 2", 0, new Date()));

		MvcResult result = mockMvc.perform(get("/patHistories").param("patId", "0")).andExpect(status().isOk())
				.andReturn();
		List<NoteDTO> noteDTOresult = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<NoteDTO>>() {
				});

		assertNotNull(noteDTOresult);
		assertTrue(noteDTOresult.contains(firstNote));
		assertTrue(noteDTOresult.contains(secondNote));
		assertTrue(noteDTOresult.size() == 2);
	}

	@Test
	public void testGetNoteById() throws Exception {
		Date date = new Date();
		String content = "test note";
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, content, 0, date));

		MvcResult result = mockMvc.perform(get("/patHistory").param("noteId", noteToInsert.getId()))
				.andExpect(status().isOk()).andReturn();
		NoteDTO noteResult = objectMapper.readValue(result.getResponse().getContentAsString(), NoteDTO.class);

		assertNotNull(noteResult);
		assertNotNull(noteResult.getId());
		assertTrue(noteResult.getNote().equals(content));
		assertTrue(noteResult.getPatientId() == 0);
		assertTrue(date.equals(noteResult.getDate()));

	}

	@Test
	public void testAddNote() throws JsonProcessingException, Exception {
		Date date = new Date();
		String content = "test note";
		int patientId = 0;
		int oldSize = noteService.getAllNotes(patientId).size();

		NoteDTO noteToInsert = new NoteDTO(null, content, patientId, date);

		MvcResult result = mockMvc.perform(post("/patHistory/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteToInsert))).andExpect(status().isOk()).andReturn();

		NoteDTO noteInserted = objectMapper.readValue(result.getResponse().getContentAsString(), NoteDTO.class);

		List<NoteDTO> allNotes = noteService.getAllNotes(patientId);

		assertTrue(allNotes.size() == oldSize + 1);
		assertTrue(allNotes.contains(noteInserted));

	}

	@Test
	public void testDeleteNote() throws Exception {
		Date date = new Date();
		String content = "test note";
		int patientId = 0;
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, content, patientId, date));
		int sizeAfterInsertion = noteService.getAllNotes(patientId).size();

		mockMvc.perform(delete("/patHistory/delete").param("id", noteToInsert.getId())).andExpect(status().isOk());

		List<NoteDTO> allNotes = noteService.getAllNotes(patientId);

		assertTrue(allNotes.size() == sizeAfterInsertion - 1);
		assertFalse(allNotes.contains(noteToInsert));
	}

	@Test
	public void testUpdateNote() throws JsonProcessingException, Exception {
		Date oldDate = new Date();
		Date newDate = Date.from(oldDate.toInstant().plus(1, ChronoUnit.DAYS));
		String oldContent = "test note";
		String newContent = "this note has been updated ! ";
		int oldPatientId = 0;
		int newPatientId = 1;
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, oldContent, oldPatientId, oldDate));
		NoteDTO noteUpdated = new NoteDTO(noteToInsert.getId(), newContent, newPatientId, newDate);

		MvcResult result = mockMvc.perform(put("/patHistory/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteUpdated))).andExpect(status().isOk()).andReturn();

		NoteDTO noteUpdatedResult = objectMapper.readValue(result.getResponse().getContentAsString(), NoteDTO.class);

		assertTrue(noteUpdatedResult.getId().equals(noteToInsert.getId()));
		assertTrue(noteUpdatedResult.getNote().equals(newContent));
		assertTrue(noteUpdatedResult.getPatientId() == newPatientId);
		assertTrue(noteUpdatedResult.getDate().equals(newDate));
		assertFalse(noteService.getAllNotes(oldPatientId).contains(noteUpdatedResult));
		assertTrue(noteService.getAllNotes(oldPatientId).size() == 0);
		assertTrue(noteService.getAllNotes(newPatientId).size() == 1);
	}

	@Test
	public void testGetOccurrencesByPatId() throws Exception {
		Date date = new Date();
		String content = "this note contains ONE occurence : Fumeur";
		int patientId = 0;
		NoteDTO noteToInsert = noteService.addNote(new NoteDTO(null, content, patientId, date));

		MvcResult result = mockMvc
				.perform(get("/getOccurrencesByPatId").param("patId", String.valueOf(noteToInsert.getPatientId())))
				.andExpect(status().isOk()).andReturn();

		int occurrences = Integer.valueOf(result.getResponse().getContentAsString());

		assertTrue(occurrences == 1);
	}
}
