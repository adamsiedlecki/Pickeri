package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;

@Entity
public class FruitType {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer slot;

    public FruitType(String name,Integer slot) {
        this.name = name;
        this.slot = slot;
    }

    public FruitType() {
    }

    public Integer getSlot() {
        if(slot==null)
            return -1;
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
