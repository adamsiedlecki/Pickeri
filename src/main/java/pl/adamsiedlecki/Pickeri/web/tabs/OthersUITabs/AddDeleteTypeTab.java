package pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitType;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddDeleteTypeTab extends VerticalLayout {

    private FruitTypeService fruitTypeService;
    private TextField typeOneField;
    private TextField typeTwoField;
    private TextField typeThreeField;
    private TextField typeFourField;
    private Button refreshButton;
    private Button saveButton;

    @Autowired
    public AddDeleteTypeTab(FruitTypeService fruitTypeService){
        this.fruitTypeService = fruitTypeService;
        initFields();
        refreshData();
    }

    private void initFields(){
        typeOneField = new TextField("Typ 1");
        typeTwoField = new TextField("Typ 2");
        typeThreeField = new TextField("Typ 3");
        typeFourField = new TextField("Typ 4");

        refreshButton = new Button("ODŚWIEŻ");
        refreshButton.addClickListener(e->refreshData());
        saveButton = new Button("ZAPISZ");
        saveButton.addClickListener(e->{
            FruitType t1 = new FruitType(typeOneField.getValue(),0);
            FruitType t2 = new FruitType(typeTwoField.getValue(),1);
            FruitType t3 = new FruitType(typeThreeField.getValue(),2);
            FruitType t4 = new FruitType(typeFourField.getValue(),3);

            List<FruitType> types = new ArrayList<>();

            addTypeIfNotEmpty(t1,types);
            addTypeIfNotEmpty(t2,types);
            addTypeIfNotEmpty(t3,types);
            addTypeIfNotEmpty(t4,types);

            try {
                fruitTypeService.addTypes(types);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            refreshData();
        });
        this.addComponents(refreshButton);
        this.addComponents(typeOneField, typeTwoField, typeThreeField, typeFourField);
        this.addComponents(saveButton);
    }

    private void addTypeIfNotEmpty(FruitType type, List<FruitType> types){
        if(type.getName()!=null&&!type.getName().equals("")){
            types.add(type);
        }
    }

    private void refreshData(){
        typeOneField.setValue(getTypeNameIfExists(0));
        typeTwoField.setValue(getTypeNameIfExists(1));
        typeThreeField.setValue(getTypeNameIfExists(2));
        typeFourField.setValue(getTypeNameIfExists(3));
    }

    private String getTypeNameIfExists(int slot){
        if(fruitTypeService.getType(slot)==null)
            return "";

        String s = fruitTypeService.getType(slot).getName();
        if(s==null){
            return "";
        }
        return s;
    }

}