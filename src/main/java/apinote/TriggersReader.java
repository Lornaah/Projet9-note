package apinote;

import java.util.ArrayList;
import java.util.List;

public class TriggersReader {

	public static int countOccurrences(List<String> notes, List<String> words) {

		List<String> wordsToFind = words;
		List<String> wordsFound = new ArrayList<>();

		int count = 0;

		for (String n : notes) {
			wordsToFind.removeAll(wordsFound);
			for (String w : wordsToFind) {
				if (n.toLowerCase().contains(w.toLowerCase())) {
					count++;
					wordsFound.add(w);
					continue;
				}
			}
		}
		return count;
	}

}
