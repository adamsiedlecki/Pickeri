package pl.adamsiedlecki.Pickeri.web.tab.noteTabs;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.service.NoteService;

@Component
@Scope("prototype")
public class DeleteNoteTab extends VerticalLayout {

    @Autowired
    public DeleteNoteTab(NoteService noteService, Environment env) {
        TextField idField = new TextField(env.getProperty("id.column"));
        Button deleteButton = new Button(env.getProperty("delete.button"));
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        deleteButton.addClickListener(e -> {
            if (NumberUtils.isDigits(idField.getValue())) {
                noteService.delete(Long.parseLong(idField.getValue()));
                idField.clear();
            } else {
                Notification.show(env.getProperty("value.is.not.a.number"));
            }
        });
        this.addComponents(idField, deleteButton);
        this.setComponentAlignment(idField, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(deleteButton, Alignment.MIDDLE_CENTER);
    }

}
