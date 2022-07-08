package apinote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apinote.repository.NoteRepository;

@Service
public class ClearDB {

	@Autowired
	NoteRepository noteRepository;

	public void clearDB() {
		noteRepository.deleteAll();
	}

}
