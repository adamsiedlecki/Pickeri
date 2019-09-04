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

    public FruitType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FruitType() {
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
