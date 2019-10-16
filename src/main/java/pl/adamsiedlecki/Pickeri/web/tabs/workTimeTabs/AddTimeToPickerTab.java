package pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.service.WorkTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@Scope("prototype")
public class AddTimeToPickerTab extends VerticalLayout {

    private static Logger log = LoggerFactory.getLogger(AddTimeToPickerTab.class);
    private WorkTimeService workTimeService;
    private TextField amountOfHoursField;
    private DateField beginDateField;
    private TextField beginTimeField;
    private DateField endDateField;
    private TextField endTimeField;
    private Environment env;

    @Autowired
    public AddTimeToPickerTab(WorkTimeService workTimeService, Environment env){
        this.env = env;
        this.workTimeService = workTimeService;
        TextField idField = new TextField(env.getProperty("id.column"));
        beginDateField = new DateField(env.getProperty("begin.date"));
        beginDateField.setDateFormat("yyyy-MM-dd");
        beginDateField.setWidth(30, Unit.PERCENTAGE);
        beginDateField.addValueChangeListener(new ClickListener());
        beginTimeField = new TextField(env.getProperty("begin.time"));
        beginTimeField.addValueChangeListener(new ClickListener());


        endDateField = new DateField(env.getProperty("end.date"));
        endDateField.setDateFormat("yyyy-MM-dd");
        endDateField.setWidth(30, Unit.PERCENTAGE);
        endDateField.addValueChangeListener(new ClickListener());
        endTimeField = new TextField(env.getProperty("end.time"));
        endTimeField.addValueChangeListener(new ClickListener());


        amountOfHoursField = new TextField(env.getProperty("hours.amount"));

        Button saveButton = new Button(env.getProperty("save.button"));

        this.addComponents(idField, beginDateField, beginTimeField, endDateField, endTimeField, amountOfHoursField, saveButton);
        //endTimeField.setValue("20:00");
        //beginTimeField.setValue("07:00");
    }

    private class ClickListener implements HasValue.ValueChangeListener {

        @Override
        public void valueChange(HasValue.ValueChangeEvent event) {

            if(beginDateField!=null && beginTimeField!=null && endDateField!=null && endTimeField!=null){
                if(isTimeValid(beginTimeField.getValue())&&isTimeValid(endTimeField.getValue())){
                    LocalDate begin = beginDateField.getValue();
                    LocalDate end = endDateField.getValue();
                    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.parse(beginTimeField.getValue()+":00"));
                    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.parse(endTimeField.getValue()+":00"));
                    amountOfHoursField.setValue(endTime+" "+beginTime);
                    log.info("Value has changed.");
                }
            }else{
                Notification.show(env.getProperty("complete.fields.notification"));
            }
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
            try{
                LocalTime.parse(time+":00");
            }catch(Exception ex){
                log.info("String is not parsable to time.");
                return false;
            }
            log.info("Time is valid.");
            return true;
        }
    }

}
