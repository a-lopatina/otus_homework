package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.domain.ClientDto;
import ru.otus.mapper.ClientDtoMapper;
import ru.otus.services.ClientService;

import java.util.List;

@Controller
public class ClientsController {

    private final ClientService clientService;
    private final ClientDtoMapper clientDtoMapper;

    public ClientsController(ClientService clientService, ClientDtoMapper clientDtoMapper) {
        this.clientService = clientService;
        this.clientDtoMapper = clientDtoMapper;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<ClientDto> clients = clientService.findAll().stream()
                .map(clientDtoMapper::fromClientToClientDto).toList();
        model.addAttribute("clients", clients);
        model.addAttribute("client", new ClientDto());
        return "clients";
    }
}
