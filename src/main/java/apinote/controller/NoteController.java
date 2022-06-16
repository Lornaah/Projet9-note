package apinote.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apinote.dto.NoteDTO;
import apinote.repository.TriggersRepository;
import apinote.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	TriggersRepository triggersRepository;

	@CrossOrigin
	@GetMapping("/patHistories")
	public List<NoteDTO> getAllNotes(@RequestParam int patId) {
		return noteService.getAllNotes(patId);
	}

	@CrossOrigin
	@GetMapping("/patHistory")
	public NoteDTO getNoteByID(@RequestParam String noteId) {
		return noteService.getNote(noteId);
	}

	@CrossOrigin
	@PostMapping("/patHistory/add")
	public NoteDTO addNote(@RequestBody @Valid NoteDTO noteDTO) {
		return noteService.addNote(noteDTO);
	}

	@CrossOrigin
	@DeleteMapping("/patHistory/delete")
	public void deleteNote(@RequestParam String id) {
		noteService.deleteNote(id);
	}

	@CrossOrigin
	@PutMapping("/patHistory/update")
	public NoteDTO updateNote(@RequestBody @Valid NoteDTO noteDTO) {
		return noteService.updateNote(noteDTO);
	}

	@CrossOrigin
	@GetMapping("/getOccurrencesByPatId")
	public int getOccurrences(@RequestParam int patId) {
		return noteService.getOccurrences(patId);
	}

}
