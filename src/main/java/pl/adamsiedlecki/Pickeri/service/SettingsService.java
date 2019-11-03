package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.SettingsDAO;
import pl.adamsiedlecki.Pickeri.entity.SettingsEntity;

import java.util.Optional;

@Service
public class SettingsService {

    private SettingsDAO settingsDAO;

    @Autowired
    public SettingsService(SettingsDAO settingsDAO){
        this.settingsDAO = settingsDAO;
    }

    public void save(SettingsEntity settingsEntity){
        settingsDAO.save(settingsEntity);
    }

    public SettingsEntity get(String key){
        Optional<SettingsEntity> optionalSettingsEntity = settingsDAO.findById(key);
        return optionalSettingsEntity.orElseGet(() -> new SettingsEntity(key, ""));
    }

    public void update(String key, String state){
        Optional<SettingsEntity> optionalSettingsEntity = settingsDAO.findById(key);
        if(optionalSettingsEntity.isPresent()){
            optionalSettingsEntity.get().setState(state);
            save(optionalSettingsEntity.get());
        }else {
            save(new SettingsEntity(key, state));
        }
    }
}
