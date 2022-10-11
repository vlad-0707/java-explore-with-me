package explorewithmeserver.repository;

import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.event.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%'))) " +
            "and e.category.id in (?2) " +
            "and e.paid = ?3 " +
            "and e.eventDate between (?4) and (?5) " +
            "and e.confirmedRequests < e.participantLimit ")
    Page<Event> searchEventsOnlyAvailable(String text,
                                          List<Long> catId,
                                          Boolean paid,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          String sort,
                                          Pageable pageable);

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.category.id in (:catId) " +
            "and e.paid = :paid " +
            "and e.eventDate < :rangeStart " +
            "and e.confirmedRequests < e.participantLimit ")
    Page<Event> searchEventsIsFuture(String text,
                                     List<Long> catId,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     Pageable pageable);


    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%'))) " +
            "and e.category.id in (?2) " +
            "and e.paid = ?3 " +
            "and e.eventDate between (?4) and (?5) ")
    Page<Event> searchEvents(String text,
                             List<Long> catId,
                             Boolean paid,
                             LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             String sort,
                             Pageable pageable);

    @Query("from Event e " +
            "where e.initiator.id in ?1 or ?1 is null " +
            "and e.state in ?2 or ?2 is null " +
            "and e.category.id in ?3 or ?3 is null " +
            "and e.eventDate between ?4 and ?5")
    Page<Event> getAllEvents(List<Long> users,
                             List<State> states,
                             List<Long> categories,
                             LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             Pageable pageable);

    @Query("from Event e where e.id in (?1)")
    List<Event> findEventsById(List<Long> ids);


    Page<Event> findEventsByInitiatorId(Long userId, Pageable pageable);

    Event findEventByIdAndInitiatorId(Long eventId, Long userId);
}
