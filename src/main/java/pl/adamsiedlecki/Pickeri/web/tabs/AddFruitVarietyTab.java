package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@Component
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
        root.add(formLayout);

        this.add(root);
    }

}
