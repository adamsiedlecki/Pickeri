package pl.adamsiedlecki.Pickeri.entity;

import com.google.common.base.Preconditions;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SettingsEntity {

    @Id
    private String name;

    private String state;

    public String getName() {
        return name;
    }

    public SettingsEntity() {
    }

    public SettingsEntity(String name, String state) {
        Preconditions.checkArgument(name!=null && !name.isBlank() && !name.isEmpty(), "Name connot " +
                "be null, empty or blank.");
        Preconditions.checkArgument(state!=null, "State cannot be null.");
        this.name = name;
        this.state = state;
    }

    public void setName(String name) {
        Preconditions.checkArgument(name!=null && !name.isBlank() && !name.isEmpty(), "Name connot " +
                "be null, empty or blank.");
        this.name = name;
    }

    public String getState() {
        Preconditions.checkArgument(state!=null, "State cannot be null.");
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
