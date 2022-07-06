package apinote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import apinote.TriggersReader;
import apinote.dto.NoteDTO;
import apinote.model.Note;
import apinote.model.Triggers;
import apinote.repository.NoteRepository;
import apinote.repository.TriggersRepository;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	TriggersRepository triggersRepository;

	@Override
	public List<NoteDTO> getAllNotes(int patId) {
		List<Note> notes = noteRepository.findAllByPatId(patId);
		List<NoteDTO> noteList = new ArrayList<>();
		notes.forEach(n -> {
			NoteDTO noteDTO = new NoteDTO(n);
			noteList.add(noteDTO);
		});
		return noteList;
	}

	@Override
	public NoteDTO getNote(String noteId) {
		ObjectId objId = new ObjectId(noteId);
		Optional<Note> note = noteRepository.findByObjectId(objId);
		if (!note.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found" + noteId);
		return new NoteDTO(note.get());
	}

	@Override
	public NoteDTO addNote(NoteDTO noteDTO) {
		Note note = new Note(noteDTO);
		return new NoteDTO(noteRepository.save(note));
	}

	@Override
	public void deleteNote(String id) {
		Note note = new Note(getNote(id));
		noteRepository.delete(note);
	}

	@Override
	public NoteDTO updateNote(@Valid NoteDTO noteDTO) {
		NoteDTO note = getNote(noteDTO.getId());
		Note newNote = new Note(noteDTO);
		ObjectId id = new ObjectId(note.getId());
		newNote.setId(id);
		return new NoteDTO(noteRepository.save(newNote));
	}

	@Override
	public int getOccurrences(int patId) {
		List<NoteDTO> notes = getAllNotes(patId);
		List<String> noteList = new ArrayList<>();
		notes.forEach(n -> {
			String note = n.getNote();
			noteList.add(note);
		});
		List<String> word = triggersRepository.findAll().stream().map(t -> t.getWord()).collect(Collectors.toList());

		return TriggersReader.countOccurrences(noteList, word);
	}

	@PostConstruct
	private void generateTriggers() {
		String[] triggers = { "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Anormal", "Cholestérol",
				"Vertige", "Rechute", "Réaction", "Anticorps" };
		Arrays.stream(triggers).forEach(this::createTrigger);
	}

	private void createTrigger(String trigger) {
		if (!triggersRepository.existsTriggersByWord(trigger)) {
			Triggers t = new Triggers();
			t.setWord(trigger);
			triggersRepository.save(t);
		}
	}

}
