package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FruitDelivery {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String productAmount;

    @Column
    private String productType;

    @Column
    private int packageAmount;

    @Column
    private String packageType;

    @Column
    private LocalDateTime deliveryTime;

    @Column
    private String comment;

}
