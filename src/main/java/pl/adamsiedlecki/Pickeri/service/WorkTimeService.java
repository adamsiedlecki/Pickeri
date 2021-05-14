package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.WorkTimeDAO;
import pl.adamsiedlecki.Pickeri.entity.WorkTime;
import pl.adamsiedlecki.Pickeri.tools.time.TimeConverter;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkTimeService {

    private final WorkTimeDAO workTimeDAO;

    @Autowired
    public WorkTimeService(WorkTimeDAO workTimeDAO){
        this.workTimeDAO = workTimeDAO;
    }

    public void save(WorkTime workTime){
        workTimeDAO.save(workTime);
    }

    public void deleteById(Long id){
        workTimeDAO.deleteById(id);
    }

    public List<WorkTime> findAll(){
        return workTimeDAO.findAll();
    }

    public String getWorkTimeSum(){
        List<WorkTime> all = findAll();
        List<Duration> durationList = all.stream().map(e->e.getDuration()).collect(Collectors.toList());
        var ref = new Object() {
            Duration sumDuration = Duration.ofMillis(0);
        };
        durationList.forEach(e-> ref.sumDuration = ref.sumDuration.plus(e));
        return TimeConverter.getString(ref.sumDuration);
    }

}
