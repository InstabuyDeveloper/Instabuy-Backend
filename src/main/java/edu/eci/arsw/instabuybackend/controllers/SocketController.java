package edu.eci.arsw.instabuybackend.controllers;

import java.util.concurrent.ConcurrentHashMap;

import edu.eci.arsw.instabuybackend.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;

import edu.eci.arsw.instabuybackend.model.Sala;

@Controller
public class SocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    private ConcurrentHashMap<String, Sala> salas = new ConcurrentHashMap<>();

    // crear sala
    @MessageMapping("/createsala.{nombreSala}")
    public void createSalasEvent(String datos, @DestinationVariable String nombreSala) throws Exception {
        if (!salas.containsKey(nombreSala)) {
            String[] informacion = datos.split(";");

            String creador = informacion[0];
            String product = informacion[1];

            Gson g = new Gson();
            Producto prouctoObj = g.fromJson(product, Producto.class);

            Sala sala = new Sala(prouctoObj, nombreSala, creador);
            salas.put(nombreSala, sala);

        }

        msgt.convertAndSend("/topic/joinsala." + nombreSala, salas.get(nombreSala));
        msgt.convertAndSend("/topic/showsala", salas.values());
    }

    // para unirse
    @MessageMapping("/joinsala.{nombreSala}")
    public void joinSalasEvent(String nameUser, @DestinationVariable String nombreSala) {
        String nick = nameUser;
        Sala temp = salas.get(nombreSala);
        temp.addUser(nick);
        msgt.convertAndSend("/topic/joinsala." + nombreSala, salas.get(nombreSala).nameMembers());
    }

    // cuando ya esta unido
    @MessageMapping("/sala.{nombreSala}")
    public void joinSalasEvent(@DestinationVariable String nombreSala) {
        msgt.convertAndSend("/topic/joinsala." + nombreSala, salas.get(nombreSala).nameMembers());

    }

    // mostrar salas
    @MessageMapping("/showsala")
    public void showSalasEvent() {
        msgt.convertAndSend("/topic/showsala", salas.values());
    }

    // para subastar como participante
    @MessageMapping("/salaSubasta.{nombreSala}")
    public void offer(String oferta, @DestinationVariable String nombreSala) {
        String[] values = oferta.split(";");
        String nick = values[0];
        double amount = Double.parseDouble(values[1]);
        salas.get(nombreSala).offer(nick, amount);
        msgt.convertAndSend("/topic/salaDatos." + nombreSala, salas.get(nombreSala).getDatos());

    }

    // obtener ofertas
    @MessageMapping("/salaDatos.{nombreSala}")
    public void getDatos(@DestinationVariable String nombreSala) {
        if (salas.containsKey(nombreSala)) {
            msgt.convertAndSend("/topic/salaDatos." + nombreSala, salas.get(nombreSala).getDatos());
        }

    }

    // Cerrar la sala
    @MessageMapping("/finSala.{nombreSala}")
    public void finSala(@DestinationVariable String nombreSala) {
        if (salas.containsKey(nombreSala)) {
            msgt.convertAndSend("/topic/finSala." + nombreSala, salas.get(nombreSala));
            salas.remove(nombreSala);
            msgt.convertAndSend("/topic/showsala", salas.values());


        }

    }

    // Asignar ganador
    @MessageMapping("/setGanador.{nombreSala}")
    public void setGanador(String ganador, @DestinationVariable String nombreSala) {
        if (salas.containsKey(nombreSala)) {
            salas.get(nombreSala).setGanador(ganador);
            msgt.convertAndSend("/topic/finSala." + nombreSala, salas.get(nombreSala));

        }

    }
}