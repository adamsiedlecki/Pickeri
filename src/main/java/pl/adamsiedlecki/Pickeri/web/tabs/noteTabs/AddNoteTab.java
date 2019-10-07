package pl.adamsiedlecki.Pickeri.web.tabs.noteTabs;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.Note;
import pl.adamsiedlecki.Pickeri.service.NoteService;

import java.time.LocalDateTime;

@Component
@Scope("prototype")
public class AddNoteTab extends VerticalLayout {

    @Autowired
    public AddNoteTab(NoteService noteService, Environment env) {
        TextArea textArea = new TextArea(env.getProperty("note"));
        Button saveButton = new Button(env.getProperty("save.button"));
        saveButton.addClickListener(e -> {
            if (textArea.isEmpty()) {
                Notification.show(env.getProperty("note.is.empty"));
            } else {
                Note note = new Note();
                note.setContent(textArea.getValue());
                note.setTime(LocalDateTime.now());
                noteService.save(note);
                textArea.clear();
            }
        });
        this.addComponents(textArea, saveButton);
    }

}
