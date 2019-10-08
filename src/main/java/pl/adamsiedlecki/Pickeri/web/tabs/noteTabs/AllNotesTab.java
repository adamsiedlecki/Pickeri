package pl.adamsiedlecki.Pickeri.web.tabs.noteTabs;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.Note;
import pl.adamsiedlecki.Pickeri.service.NoteService;

import java.util.Objects;

@Component
@Scope("prototype")
public class AllNotesTab extends VerticalLayout {

    private Grid<Note> notesGrid;
    private Button refreshButton;
    private NoteService noteService;

    @Autowired
    public AllNotesTab(Environment env, NoteService noteService) {
        this.noteService = noteService;
        notesGrid = new Grid<>();
        refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        notesGrid.addColumn(Note::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        notesGrid.addColumn(Note::getContent).setCaption(Objects.requireNonNull(env.getProperty("note.content.column")));
        this.addComponents(refreshButton, notesGrid);
        notesGrid.setWidth(80, Unit.PERCENTAGE);
        this.setComponentAlignment(refreshButton, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(notesGrid, Alignment.MIDDLE_CENTER);
        refreshData();
    }

    private void refreshData() {
        notesGrid.setItems(noteService.findAll());
    }

}
