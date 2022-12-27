package com.soa.jax.repo;

import com.soa.jax.dto.SearchDTO;
import com.soa.jax.dto.TicketDTO;
import com.soa.jax.entity.Person;
import com.soa.jax.entity.Ticket;
import com.soa.jax.entity.Venue;
import com.soa.jax.entity.inner.*;
import jakarta.ws.rs.QueryParam;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TicketRepo {
    @PersistenceContext(unitName = "ticket")
    EntityManager entityManager;

    public Ticket getTicket(Long id){
        Ticket ticket = entityManager.find(Ticket.class, id);
        if (ticket == null) {
            throw new EntityNotFoundException("Can't find Ticket for ID "
                    + id);
        }
        return ticket;
    }

    public Ticket addTicket(TicketDTO dto){
        Ticket ticket = new Ticket();
        ticket = parseDTO(ticket, dto);
        ticket = entityManager.merge(ticket);
        return ticket;
    }

    public Ticket update(TicketDTO dto){
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket = parseDTO(ticket, dto);
        ticket = entityManager.merge(ticket);
        return ticket;
    }

    public void delete(Long id){
        Ticket ticket = getTicket(id);
        entityManager.remove(ticket);
    }

    public List<Ticket> getTickets(int page, int size, TicketDTO dto, SearchDTO search){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder
                .createQuery(Ticket.class);
        Root<Ticket> from = criteriaQuery.from(Ticket.class);
        CriteriaQuery<Ticket> select = criteriaQuery.select(from);
        List<Order> orders = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        Join<Ticket, Person> personJoin = from.join("owner");
        Join<Ticket, Venue> join = from.join("venue");
        Join<Ticket, Address> addressJoin = join.join("address");
        Join<Ticket, Location> locationJoin = addressJoin.join("town");
        Join<Ticket, Coordinates> coordinatesJoin = from.join("coordinates");
        try {
            if (search.getSortAsc()) {
                select.orderBy(criteriaBuilder.asc(from.get(search.getSort())));
            } else {
                select.orderBy(criteriaBuilder.desc(from.get(search.getSort())));
            }
        }
        catch(IllegalArgumentException e){
            try {
                if (search.getSortAsc()) {
                    select.orderBy(criteriaBuilder.asc(personJoin.get(search.getSort())));
                } else {
                    select.orderBy(criteriaBuilder.desc(personJoin.get(search.getSort())));
                }
            }
            catch(IllegalArgumentException e2){
                try {
                    if (search.getSortAsc()) {
                        select.orderBy(criteriaBuilder.asc(join.get(search.getSort())));
                    } else {
                        select.orderBy(criteriaBuilder.desc(join.get(search.getSort())));
                    }
                }
                catch(IllegalArgumentException e3){
                    try {
                        if (search.getSortAsc()) {
                            select.orderBy(criteriaBuilder.asc(addressJoin.get(search.getSort())));
                        } else {
                            select.orderBy(criteriaBuilder.desc(addressJoin.get(search.getSort())));
                        }
                    }
                    catch(IllegalArgumentException e4){
                        try {
                            if (search.getSortAsc()) {
                                select.orderBy(criteriaBuilder.asc(locationJoin.get(search.getSort())));
                            } else {
                                select.orderBy(criteriaBuilder.desc(locationJoin.get(search.getSort())));
                            }
                        }
                        catch(IllegalArgumentException e5){
                            if (search.getSortAsc()) {
                                select.orderBy(criteriaBuilder.asc(coordinatesJoin.get(search.getSort())));
                            } else {
                                select.orderBy(criteriaBuilder.desc(coordinatesJoin.get(search.getSort())));
                            }
                        }
                    }
                }
            }
        }
        if (search.getFilter()!=null){
            if (search.getFilter().getName()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("name"), search.getFilter().getName()));
            }
            if (search.getFilter().getType()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("type"), search.getFilter().getType()));
            }
            if (search.getFilter().getComment()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("comment"), search.getFilter().getComment()));
            }
            if (search.getFilter().getDiscount()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("discount"), search.getFilter().getDiscount()));
            }
            if (search.getFilter().getPrice()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("price"), search.getFilter().getPrice()));
            }
            if (search.getFilter().getCreationDate()!=null){
                predicates.add(
                        criteriaBuilder.equal(from.get("creationDate"), search.getFilter().getCreationDate()));
            }
            if (search.getFilter().getOwner()!=null){
                if (search.getFilter().getOwner().getPersonName()!=null){
                    predicates.add(
                            criteriaBuilder.equal(personJoin.get("personName"), search.getFilter().getOwner().getPersonName()));
                }
            }
            if (search.getFilter().getCoordinates()!=null){
                if (search.getFilter().getCoordinates().getX()!=null){
                    predicates.add(
                            criteriaBuilder.equal(coordinatesJoin.get("x"), search.getFilter().getCoordinates().getX()));
                }
                if (search.getFilter().getCoordinates().getY()!=null){
                    predicates.add(
                            criteriaBuilder.equal(coordinatesJoin.get("y"), search.getFilter().getCoordinates().getY()));
                }
            }

            if (search.getFilter().getVenue()!=null){
                if (search.getFilter().getVenue().getVenueName()!=null){
                    predicates.add(
                            criteriaBuilder.equal(join.get("venueName"), search.getFilter().getVenue().getVenueName()));
                }
                if (search.getFilter().getVenue().getVenueCapacity()!=null){
                    predicates.add(
                            criteriaBuilder.equal(join.get("venueCapacity"), search.getFilter().getVenue().getVenueCapacity()));
                }
                if (search.getFilter().getVenue().getVenueType()!=null){
                    predicates.add(
                            criteriaBuilder.equal(join.get("venueType"), search.getFilter().getVenue().getVenueType()));
                }
                if (search.getFilter().getVenue().getAddress()!=null){
                    if (search.getFilter().getVenue().getAddress().getStreet()!=null) {
                        predicates.add(
                                criteriaBuilder.equal(addressJoin.get("street"), search.getFilter().getVenue().getAddress().getStreet()));
                    }
                    if (search.getFilter().getVenue().getAddress().getZipCode()!=null) {
                        predicates.add(
                                criteriaBuilder.equal(addressJoin.get("zipCode"), search.getFilter().getVenue().getAddress().getZipCode()));
                    }
                    if (search.getFilter().getVenue().getAddress().getTown()!=null){
                        if (search.getFilter().getVenue().getAddress().getTown().getLocationName()!=null){
                            predicates.add(
                                    criteriaBuilder.equal(locationJoin.get("locationName"), search.getFilter().getVenue().getAddress().getTown().getLocationName()));
                        }
                        if (search.getFilter().getVenue().getAddress().getTown().getLocationX()!=null){
                            predicates.add(
                                    criteriaBuilder.equal(locationJoin.get("locationX"), search.getFilter().getVenue().getAddress().getTown().getLocationX()));
                        }
                        if (search.getFilter().getVenue().getAddress().getTown().getLocationX()!=null){
                            predicates.add(
                                    criteriaBuilder.equal(locationJoin.get("locationY"), search.getFilter().getVenue().getAddress().getTown().getLocationY()));
                        }
                    }
                }
            }
        }

       // criteriaQuery.where(criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
        criteriaQuery.select(from).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Ticket> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    public Ticket getByMinCoords(){
        Query q = entityManager.createNativeQuery("select * from ticket order by coordinates_id asc limit 1;", Ticket.class);
        return (Ticket) q.getSingleResult();
    }

    public Integer countCommentLess(String value){
        Query q = entityManager.createNativeQuery("select count(*) from ticket where comment < ?;");
        q.setParameter(1, value);
        return ((Number) q.getSingleResult()).intValue();
    }

    public List<Ticket> getAllDiscountLess(Integer value){
        TypedQuery<Ticket> q = entityManager.createQuery("select t from Ticket t where t.discount < :disc", Ticket.class).setParameter("disc", value);
        return q.getResultList();
    }

    private Ticket parseDTO(Ticket ticket, TicketDTO dto){
        ticket.setComment(dto.getComment());
        ticket.setName(dto.getName());
        ticket.setDiscount(dto.getDiscount());
        Coordinates coords = new Coordinates();
        coords.setX(dto.getCoordinates().getX());
        coords.setY(dto.getCoordinates().getY());
        coords = entityManager.merge(coords);
        ticket.setCoordinates(coords);
        Person owner = new Person();
        owner.setPersonName(dto.getOwner().getPersonName());
        owner = entityManager.merge(owner);
        ticket.setOwner(owner);
        ticket.setPrice(dto.getPrice());
        ticket.setType(TicketType.valueOf(dto.getType()));
        Venue venue = new Venue();
        venue.setVenueName(dto.getVenue().getVenueName());
        venue.setVenueType(VenueType.valueOf(dto.getVenue().getVenueType()));
        venue.setVenueCapacity(dto.getVenue().getVenueCapacity());
        Address address = new Address();
        address.setZipCode(dto.getVenue().getAddress().getZipCode());
        address.setStreet(dto.getVenue().getAddress().getStreet());
        Location location = new Location();
        location.setLocationName(dto.getVenue().getAddress().getTown().getLocationName());
        location.setLocationX(dto.getVenue().getAddress().getTown().getLocationX());
        location.setLocationY(dto.getVenue().getAddress().getTown().getLocationY());
        location = entityManager.merge(location);
        address.setTown(location);
        address = entityManager.merge(address);
        venue.setAddress(address);
        venue = entityManager.merge(venue);
        ticket.setVenue(venue);
        ticket.setCreationDate(LocalDate.parse(dto.getCreationDate()));
        return ticket;
    }


}
