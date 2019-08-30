package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@SpringComponent
@Scope("prototype")
public class AddFruitVarietyTab extends VerticalLayout {

    private FruitVarietyService fruitVarietyService;
    private VerticalLayout root;
    private TextField name;
    private TextField comment;
    private Button save;
    private FormLayout formLayout;

    @Autowired
    public AddFruitVarietyTab(FruitVarietyService fruitVarietyService){
        this.fruitVarietyService = fruitVarietyService;
        initComponents();
        save.addClickListener(e->{
            if(!name.isEmpty()){
                FruitVariety newVariety = new FruitVariety(name.getValue(),comment.getValue());
                fruitVarietyService.save(newVariety);
                name.clear();
                comment.clear();
            }
        });
    }

    private void initComponents(){
        root = new VerticalLayout();
        name = new TextField("Nazwa");
        comment = new TextField("Komentarz, opis");
        save = new Button("Zapisz");
        formLayout = new FormLayout(name, comment, save);
        root.addComponent(formLayout);
        this.addComponent(root);
    }

}
