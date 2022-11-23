package com.mindhub.homebanking.service;


import ch.qos.logback.core.net.server.Client;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Cliente;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {





    List<ClientDTO> getClientsDTO();

    ClientDTO getClientDTO(Long id);

    Cliente getClientAuthentic (Authentication authentication);

    Cliente findByemail (String email);

    void saveClient (Cliente cliente);

}
