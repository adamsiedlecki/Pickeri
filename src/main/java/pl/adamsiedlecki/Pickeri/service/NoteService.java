package pl.adamsiedlecki.Pickeri.service;

import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.NoteDAO;
import pl.adamsiedlecki.Pickeri.entity.Note;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteDAO noteDAO;

    public NoteService(NoteDAO dao) {
        this.noteDAO = dao;
    }

    public List<Note> findAll() {
        return noteDAO.findAll();
    }

    public Optional<Note> getNoteWithId(Long id) {
        return noteDAO.findById(id);
    }

    public Note getLastNote() {
        List<Note> notes = findAll();
        Note last = new Note();
        for (Note n : notes) {
            if (n.getTime().isAfter(last.getTime())) {
                last = n;
            }
        }
        return last;
    }

}
