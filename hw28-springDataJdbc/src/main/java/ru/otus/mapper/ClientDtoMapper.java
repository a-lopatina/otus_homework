package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.domain.ClientDto;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ClientDtoMapper {

    public Client fromClientDtoToClient(ClientDto clientDto) {
        Set<Phone> phones = Stream.of(clientDto.getPhones().split(","))
                .map(String::trim)
                .map(Phone::new)
                .collect(Collectors.toSet());
        return new Client(null, clientDto.getName(), new Address(clientDto.getAddress()), phones);
    }

    public ClientDto fromClientToClientDto(Client client) {
        String phones = client.getPhones().stream()
                .map(Phone::getNumber)
                .collect(Collectors.joining(","));
        return new ClientDto(client.getId(), client.getName(), client.getAddress().getStreet(), phones);
    }
}
