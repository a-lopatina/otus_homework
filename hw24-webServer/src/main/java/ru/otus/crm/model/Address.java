package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "street")
    private String street;

    public Address() {
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }
}
