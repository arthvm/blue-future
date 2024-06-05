package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta classe gerencia os eventos no sistema.
 * Ela fornece métodos para adicionar um evento e obter todos os eventos.
 */
public class EventManager {
    /**
     * Um mapa para armazenar os eventos, com a organização como chave e uma lista de eventos como valor.
     */
    private static Map<Organization, List<Event>> events = new HashMap<>();

    /**
     * Construtor privado para prevenir a instanciação.
     */
    private EventManager(){
    }

    /**
     * Construtor privado para inicializar o mapa de eventos.
     * @param eventList O mapa inicial de eventos.
     */
    private EventManager(Map<Organization, List<Event>> eventList) {
        EventManager.events = eventList;
    }

    /**
     * Adiciona um evento à lista de eventos de uma organização.
     * @param organization A organização que está hospedando o evento.
     * @param event O evento a ser adicionado.
     * @throws IllegalArgumentException Se a organização ou o evento for nulo.
     */
    public static void addEvent(Organization organization, Event event){
        if(organization == null) throw new IllegalArgumentException("Organização não pode ser nula");
        if(event == null) throw new IllegalArgumentException("Evento não pode ser nulo");

        List<Event> orgEvents = EventManager.events.computeIfAbsent(organization, org -> new ArrayList<>());
        orgEvents.add(event);
    }

    /**
     * @return Uma lista de todos os eventos.
     */
    public static List<Event> getEvents(){
        List<Event> allEvents = new ArrayList<>();

        int index = 1;
        for(Map.Entry<Organization, List<Event>> entry: EventManager.events.entrySet()){
            Organization org = entry.getKey();
            System.out.println("---------------------" + org.getName() + "---------------------");
            List<Event> orgEvents = entry.getValue();

            for(Event orgEvent: orgEvents){
                System.out.println(index + "." + orgEvent);
                index++;
            }

            allEvents.addAll(orgEvents);
        }

        return allEvents;
    }
}