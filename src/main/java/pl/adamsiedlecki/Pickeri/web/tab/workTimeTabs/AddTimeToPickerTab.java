package pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs;

import com.google.common.base.Preconditions;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.entity.WorkTime;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.WorkTimeService;
import pl.adamsiedlecki.Pickeri.tools.time.TimeConverter;

import java.time.*;
import java.util.Optional;

@Component
@Scope("prototype")
public class AddTimeToPickerTab extends VerticalLayout {

    private static Logger log = LoggerFactory.getLogger(AddTimeToPickerTab.class);
    private VerticalLayout root;
    private WorkTimeService workTimeService;
    private FruitPickerService fruitPickerService;
    private TextField amountOfHoursField;
    private DateField beginDateField;
    private TextField beginTimeField;
    private DateField endDateField;
    private TextField endTimeField;
    private Environment env;
    //private TextField idField;
    private ComboBox<String> employeesComboBox;

    @Autowired
    public AddTimeToPickerTab(WorkTimeService workTimeService, Environment env, FruitPickerService fruitPickerService){
        root = new VerticalLayout();
        this.fruitPickerService = fruitPickerService;
        this.env = env;
        this.workTimeService = workTimeService;
        employeesComboBox = new ComboBox<>(env.getProperty("fruit.pickers.list"));
        employeesComboBox.setEmptySelectionAllowed(false);
        refreshEmployeesList();
        //idField = new TextField(env.getProperty("id.column"));
        beginDateField = new DateField(env.getProperty("begin.date"));
        beginDateField.setDateFormat("yyyy-MM-dd");
        beginDateField.addValueChangeListener(new ClickListener());
        beginTimeField = new TextField(env.getProperty("begin.time"));
        beginTimeField.addValueChangeListener(new ClickListener());
        Button saveButton = new Button(env.getProperty("save.button"));


        endDateField = new DateField(env.getProperty("end.date"));
        endDateField.setDateFormat("yyyy-MM-dd");
        endDateField.addValueChangeListener(new ClickListener());
        endTimeField = new TextField(env.getProperty("end.time"));
        endTimeField.addValueChangeListener(new ClickListener());


        amountOfHoursField = new TextField(env.getProperty("hours.amount"));
        amountOfHoursField.setEnabled(false);
        amountOfHoursField.setDescription(env.getProperty("decimal.hours.description"));

        root.addComponents(employeesComboBox, beginDateField, beginTimeField, endDateField, endTimeField, amountOfHoursField, saveButton);
        this.addComponent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        beginTimeField.setValue("07:00");
        endTimeField.setValue("20:00");
        beginDateField.setValue(LocalDate.now());
        endDateField.setValue(LocalDate.now());

        saveButton.addClickListener(e->{
            Optional<WorkTime> optionalWorkTime = getWorkTimeObject();
            if(optionalWorkTime.isPresent()&&optionalWorkTime.get().getFruitPicker()!=null){
                workTimeService.save(optionalWorkTime.get());
                Notification.show(env.getProperty("work.time.saved.successfully"), Notification.Type.TRAY_NOTIFICATION);
            }else{
                Notification.show(env.getProperty("wrong.values.provided"), Notification.Type.ERROR_MESSAGE);
            }
        });

    }

    public void refreshEmployeesList(){
        employeesComboBox.setItems(fruitPickerService.findAll().stream().map(FruitPicker::getIdNameLastName));
    }

    private Optional<WorkTime> getWorkTimeObject(){
        if(beginDateField!=null && beginTimeField!=null && endDateField!=null && endTimeField!=null){
            if(isTimeValid(beginTimeField.getValue())&&isTimeValid(endTimeField.getValue())){
                LocalDate begin = beginDateField.getValue();
                LocalDate end = endDateField.getValue();

                if(begin!=null&end!=null){
                    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.parse(beginTimeField.getValue()+":00"));
                    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.parse(endTimeField.getValue()+":00"));

                    Duration workTime = Duration.between(beginTime, endTime);

                    if(employeesComboBox.getValue()!=null){
                        String[] tab = employeesComboBox.getValue().split(" ");
                        Preconditions.checkArgument(NumberUtils.isDigits(tab[0]), "Id must be digits!");
                        long id = Long.parseLong(tab[0]);
                        Optional<FruitPicker> fp = fruitPickerService.getFruitPickerById(id);
                        if(fp.isPresent()){
                            return  Optional.of(new WorkTime(fp.get(), beginTime, endTime, workTime));
                        }
                    }else{
                        return Optional.of(new WorkTime(null, beginTime, endTime, workTime));
                    }

                }else{
                    if(fieldsNotEmpty()){
                        amountOfHoursField.clear();
                    }
                }

            }else{
                if(fieldsNotEmpty()){
                    amountOfHoursField.clear();
                }
            }
        }else{
            Notification.show(env.getProperty("complete.fields.notification"), Notification.Type.ERROR_MESSAGE);
        }
        return Optional.empty();
    }

    private boolean isTimeValid(String time){
        if(time==null){
            log.info("Time is null.");
            return false;
        }
        if(time.length()!=5){
            log.info("Time String value has illegal length: "+time.length());
            return false;
        }
        if(time.charAt(2)!=':'){
            log.info("There is no ':' in the time. ");
            return false;
        }
        char c = time.charAt(0);
        if(c<48||c>53){
            log.info("There are numbers greater than 5 and smaller than zero.  ");return false;
        }
        c = time.charAt(3);
        if(c<48||c>53){
            log.info("There are numbers greater than 5 and smaller than zero.  ");return false;
        }
        c = time.charAt(1);
        if(c<48||c>57){
            log.info("There are non numeric values in the time. ");return false;
        }
        c = time.charAt(4);
        if(c<48||c>57){
            log.info("There are non numeric values in the time.  ");return false;
        }

        try{
            LocalTime.parse(time+":00");
        }catch(Exception ex){
            log.info("String is not parsable to time.");
            return false;
        }
        log.info("Time is valid.");
        return true;
    }

    private boolean fieldsNotEmpty(){
        if(beginDateField.isEmpty() || beginTimeField.isEmpty() || endDateField.isEmpty() || endTimeField.isEmpty()){
            return false;
        }
        return true;
    }

    private class ClickListener implements HasValue.ValueChangeListener {
        @Override
        public void valueChange(HasValue.ValueChangeEvent event) {
            Optional<WorkTime> optionalWorkTime = getWorkTimeObject();
            if(optionalWorkTime.isPresent()){
                WorkTime  workTime = optionalWorkTime.get();
                String time = TimeConverter.getString(workTime);
                amountOfHoursField.setValue(time);
            }
        }
    }

}
