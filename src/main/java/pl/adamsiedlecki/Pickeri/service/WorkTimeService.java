package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.WorkTimeDAO;
import pl.adamsiedlecki.Pickeri.entity.WorkTime;

import java.util.List;

@Service
public class WorkTimeService {

    private WorkTimeDAO workTimeDAO;

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

}
