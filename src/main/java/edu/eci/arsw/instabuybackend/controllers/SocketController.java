package edu.eci.arsw.instabuybackend.controllers;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.instabuybackend.model.Sala;

@Controller
public class SocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    private ConcurrentHashMap<String, Sala> salas = new ConcurrentHashMap<>();

    @MessageMapping("/createsala.{nombre}")
    public void createSalasEvent(String cuentaF, @DestinationVariable String nombre) throws Exception {
        if (!salas.containsKey(nombre)) {
            String[] datos = cuentaF.split(";");
            String product = datos[0];
            String name = datos[1];

            Sala sala = new Sala(product, nombre);
            sala.addUser(name);
            salas.put(nombre, sala);

        }

        msgt.convertAndSend("/topic/joinsala." + nombre, salas.get(nombre));
        msgt.convertAndSend("/topic/showsala", salas.values());
    }

    // para unirse
    @MessageMapping("/joinsala.{nombre}")
    public void joinSalasEvent(String cuentaF, @DestinationVariable String nombre) {
        /*
         * String[] datos = cuentaF.split(";"); Long id = Long.valueOf(datos[0]); String
         * correo = datos[1]; String contrasena = datos[2];
         */
        String nick = cuentaF;
        // Cuenta cuenta = new Cuenta(id, correo, contrasena, nick);
        Sala temp = salas.get(nombre);
        temp.addUser(nick);
        msgt.convertAndSend("/topic/joinsala." + nombre, salas.get(nombre).nameMembers());
        msgt.convertAndSend("/topic/showsala", salas.values());
    }

    // cuando ya esta unido
    @MessageMapping("/sala.{nombre}")
    public void joinSalasEvent(@DestinationVariable String nombre) {
        msgt.convertAndSend("/topic/joinsala." + nombre, salas.get(nombre).nameMembers());

    }

    @MessageMapping("/showsala")
    public void showSalasEvent() {
        msgt.convertAndSend("/topic/showsala", salas.values());
    }

    // para subastar como participante
    @MessageMapping("/salaSubasta.{nombre}")
    public void offer(String val, @DestinationVariable String nombre) {
        String[] values = val.split(";");
        String name = values[0];
        double amount = Double.parseDouble(values[1]);
        salas.get(nombre).offer(name, amount);
        msgt.convertAndSend("/topic/salaDatos." + nombre, salas.get(nombre).getDatos());

    }

    @MessageMapping("/salaDatos.{nombre}")
    public void getDatos(@DestinationVariable String nombre) {
        if (salas.containsKey(nombre)) {
            msgt.convertAndSend("/topic/salaDatos." + nombre, salas.get(nombre).getDatos());
        }

    }

    @MessageMapping("/finSala.{nombre}")
    public void finSala(@DestinationVariable String nombre) {
        if (salas.containsKey(nombre)) {
            msgt.convertAndSend("/topic/finSala." + nombre, salas.get(nombre));
            salas.remove(nombre);
        }

    }

}