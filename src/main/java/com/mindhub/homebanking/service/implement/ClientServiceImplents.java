package com.mindhub.homebanking.service.implement;


import ch.qos.logback.core.net.server.Client;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplents implements ClientService {


    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<ClientDTO> getClientsDTO() {
        return clienteRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return clienteRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public Cliente getClientAuthentic(Authentication authentication) {
        return clienteRepository.findByEmail(authentication.getName());
    }

    @Override
    public Cliente findByemail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Cliente cliente) {
        clienteRepository.save(cliente);
    }


}
