package pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.WorkTime;
import pl.adamsiedlecki.Pickeri.service.WorkTimeService;
import pl.adamsiedlecki.Pickeri.tools.time.TimeComparator;
import pl.adamsiedlecki.Pickeri.tools.time.TimeConverter;

import java.util.Objects;

@Component
@Scope("prototype")
public class AllWorkTimesTab extends VerticalLayout {

    private Grid<WorkTime> workTimeGrid;
    private WorkTimeService workTimeService;

    public AllWorkTimesTab(WorkTimeService workTimeService, Environment env){
        this.workTimeService = workTimeService;
        workTimeGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        workTimeGrid.setWidth(80,Unit.PERCENTAGE);
        workTimeGrid.addColumn(WorkTime::getPickerInfo).setCaption(Objects.requireNonNull(env.getProperty("employee.info")));
        workTimeGrid.addColumn(WorkTime::getDurationPlainString).setCaption(Objects.requireNonNull(env.getProperty("worktime.registry")+" [h]"))
                .setComparator((v1, v2)-> TimeComparator.compare(TimeConverter.getString(v1.getDuration()), TimeConverter.getString(v2.getDuration())));
        workTimeGrid.addColumn(WorkTime::getStartTimePlainString).setCaption(Objects.requireNonNull(env.getProperty("begin.date")));
        workTimeGrid.addColumn(WorkTime::getEndTimePlainString).setCaption(Objects.requireNonNull(env.getProperty("end.date")));
        this.addComponents(refreshButton, workTimeGrid);
        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        refreshButton.addClickListener(e->refresh());
        refresh();
    }

    private void refresh(){
        workTimeGrid.setItems(workTimeService.findAll());
    }

}
