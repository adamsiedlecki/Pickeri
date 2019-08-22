package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;

@Entity
public class FruitPicker {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Character gender;


}
