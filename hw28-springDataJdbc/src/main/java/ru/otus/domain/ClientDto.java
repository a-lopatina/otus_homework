package ru.otus.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private long id;
    private String name;
    private String address;
    private String phones;

    public ClientDto() {
    }

    public ClientDto(long id, String name, String address, String phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
