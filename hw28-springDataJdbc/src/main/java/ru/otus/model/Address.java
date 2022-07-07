package ru.otus.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("address")
public class Address {

    @Id
    private final Long id;

    @Column("street")
    private final String street;

    @Column("client_id")
    private final Long clientId;

    @PersistenceConstructor
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(String street) {
        this(null, street, null);
    }
}
