package pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@SpringComponent
@Scope("prototype")
public class AddFruitVarietyTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField name;
    private TextField comment;
    private Button save;
    private Environment env;

    @Autowired
    public AddFruitVarietyTab(FruitVarietyService fruitVarietyService, Environment environment) {
        this.env = environment;
        initComponents();
        save.addClickListener(e -> {
            if (!name.isEmpty()) {
                FruitVariety newVariety = new FruitVariety(name.getValue(), comment.getValue());
                fruitVarietyService.save(newVariety);
                name.clear();
                comment.clear();
            }
        });
    }

    private void initComponents() {
        root = new VerticalLayout();
        name = new TextField(env.getProperty("name.field"));
        comment = new TextField(env.getProperty("comment.field"));
        save = new Button(env.getProperty("save.button"));
        FormLayout formLayout = new FormLayout(name, comment, save);
        root.addComponent(formLayout);
        this.addComponent(root);
    }

}
