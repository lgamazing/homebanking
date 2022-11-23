package com.mindhub.homebanking.repositories;
// Json Java Scrip Object notation.
import com.mindhub.homebanking.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

//  estate: el estado de los datos.
@RepositoryRestResource //La anotacion va a trabajar con las restricciones de REST: En formato JSON o XML y trabajar con las peticiones HTTP: GET, POST, PATCH, PUT y DELETE. Representational Estate Transfer, REST es una arquitectura. Trabajo con rest porque quiero trabajar con datos de tipo JSON
public interface ClienteRepository extends JpaRepository<Cliente,Long> { // La interfaz es la manera de comunicarme puente, coneccion entre dos cosas.
    //extendes: hereda los metodos de JpaRepository, a tu repository.
    //JPA: Java persistence Api. Persistencia de los datos dentro de SPRING.
    //<> Clase a guardar y el dato de la primaryKey.
    //No trabaja con datos primitivos.  con Wrapper.
    Cliente findByEmail(String email);

    Set<Cliente> findByprimerNombre(String primerNombre);


}
