package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity //Entity: Crea En la base de datos una tabla con el nombre especificado. JpaSpring.
public class Cliente { //Clase porque quiero represesntar un objeto en la vida real, de acceso publico. Crea el objeto en cuestion.


    @Id //primaryKey
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GeneratedValue Nos permite generar el valor automatico, sin que se pise con otra tabla.
    @GenericGenerator(name = "native", strategy = "native") //Este nos permite saber la estrategia de como se van a generar.
    private long id; // atributos.

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER) //binireccional afecta por ambas partes, "UN" solo Cliente puede tener "MUCHAS" Cuentas.
    Set<Account> Accounts = new HashSet<>(); //Fetch Nos permite decirle a la base de datos como queremos los datos.

    @OneToMany(mappedBy = "Client", fetch = FetchType.EAGER) //MapBy: Asocia el objeto. es loque quiero relacionar.
    Set<ClientLoan> ClientLoan = new HashSet<>(); //set es una collecion de de elementos., HashSet Nos permite guardar espacio en la memoria.

    @OneToMany(mappedBy = "Clientee", fetch = FetchType.EAGER)
    Set<Card> Cards = new HashSet<>();
//en base al contexto


    private String primerNombre; // atributos.
    private String apellido; // atributos.
    private String email; // atributos.

    private String password;

    public Cliente() { //constructor vacio. Sirve para que lo lea la aplicacion. Despues se sobreescribe con el constructor original. Te permite la creacion.
    }

    public Cliente(String primerNombre, String apellido, String email, String password) {
        this.primerNombre = primerNombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public long getId() { //Metodos ACCESORES nos permiten acceder a sus atributos y setearlos. Un método "setter" sirve para "cargar un valor" (asignar un valor a una variable).
        // Un método "getter" sirve para "retornar el valor" (solo devolver la información del atributo para quién la solicite).
        return id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    } //void no te retorna nada, solo asigna un valor.

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return Accounts;
    }

    @JsonIgnore
    public Set<Card> getCards() {
        return Cards;
    }

    @JsonIgnore //La anotacion evita la recursividad de los datos.
    public Set<ClientLoan> getLoans() {
        return ClientLoan;
    }


    public void addAccount(Account cuenta) {
        cuenta.setCliente(this);
        Accounts.add(cuenta);
    }
    public void addCards(Card card) {
        card.setClientee(this);
        Cards.add(card);
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", primerNombre='" + primerNombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", Accounts=" + Accounts +
                '}';
    }
}
