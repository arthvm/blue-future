package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private static Map<Organization, List<Event>> events = new HashMap<>();

    public EventManager(){

    }

    public EventManager(Map<Organization, List<Event>> eventList) {
        events = eventList;
    }

    public void addEvent(Organization organization, Event event){
        if(organization == null) throw new IllegalArgumentException("Organization can't be null");
        if(event == null) throw new IllegalArgumentException("Event can't be null");

        List<Event> orgEvents = events.computeIfAbsent(organization, org -> new ArrayList<>());
        orgEvents.add(event);
    }

    public List<Event> getEvents(){
        List<Event> allEvents = new ArrayList<>();

        int index = 1;
        for(Map.Entry<Organization, List<Event>> entry: events.entrySet()){
            Organization org = entry.getKey();
            System.out.println("---------------------" + org.getName() + "---------------------");
            List<Event> orgEvents = entry.getValue();

            for(Event orgEvent: orgEvents){
                System.out.println(index + "." + orgEvent);
            }

            index++;
            allEvents.addAll(orgEvents);
        }

        return allEvents;
    }
}
