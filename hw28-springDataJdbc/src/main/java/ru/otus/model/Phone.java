package ru.otus.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("phone")
public class Phone {

    @Id
    private final Long id;

    @Column("number")
    private final String number;

    @Column("client_id")
    private final Long clientId;

    @PersistenceConstructor
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(String number) {
        this(null, number, null);
    }

}
