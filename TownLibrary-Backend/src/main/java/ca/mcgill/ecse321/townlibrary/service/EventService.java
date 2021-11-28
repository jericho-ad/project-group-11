package ca.mcgill.ecse321.townlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.townlibrary.model.*;
import ca.mcgill.ecse321.townlibrary.repository.*;

import java.util.*;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    /**
     * Creates an event
     *
     * @param lib           The library
     * @param id            The event id
     * @param name          The event name
     * @param transaction   The transaction
     *
     * @return              The event instance
     *
     * @throws IllegalArgumentException invalid inputs
     */
    @Transactional
    public Event createEvent(Library lib, String name) {
        final StringBuilder err = new StringBuilder();
        if (lib == null) {
            err.append("Invalid inputs");
        }
        final Event e = new Event();
        e.setLibrary(lib);
        e.setName(name);
        eventRepository.save(e);

        if (err.length() != 0) {
            throw new IllegalArgumentException(err.toString());
        }
        return e;
    }

    @Transactional
    public boolean deleteEvent(int eventId) {
        final Event e = eventRepository.findEventById(eventId);
        if (e == null) throw new IllegalArgumentException("EVENT-NOT-FOUND");
        eventRepository.delete(e);
        return (eventRepository.findById(eventId) == null);
    }

    /**
     * Sets a transaction for an event
     *
     * @param e             The event
     * @param transaction   The transaction
     *
     * @throws IllegalArgumentException if either event or transaction is null
     */

    @Transactional
    public void setEventTransaction(Event e, Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Invalid transaction");
        }
        if (e == null) {
            throw new IllegalArgumentException("Invalid event");
        }

        e.setTransaction(transaction);
        eventRepository.save(e);
    }
    /**
     * 
     * @param id
     * @return
     */

    @Transactional
    public void addUserToEvent(Event e, UserRole user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        if (e == null) {
            throw new IllegalArgumentException("Invalid event");
        }
        e.getUsers().add(user);;
    }

    @Transactional
    public void removeUserFromEvent(Event e, UserRole user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        if (e == null) {
            throw new IllegalArgumentException("Invalid event");
        }
        e.getUsers().remove(user);
    }

    /**
     * Retrieves an event by its id
     *
     * @param id    The event's id
     *
     * @return The event or null
     */
    @Transactional
    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves an event by its transaction
     *
     * @param transaction   The event's transaction
     *
     * @return The event
     */
    @Transactional
    public Event getEventByTransaction(Transaction transaction) {
        return eventRepository.findByTransaction(transaction);
    }

    /**
     * Retrieves all the events in the system
     *
     * @return all the events
     */
    @Transactional
    public List<Event> getAllEvents() {
        final ArrayList<Event> events = new ArrayList<>();
        for (final Event e : eventRepository.findAll()) {
            events.add(e);
        }
        return events;
    }
}