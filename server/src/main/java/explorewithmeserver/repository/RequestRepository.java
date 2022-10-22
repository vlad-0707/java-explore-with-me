package explorewithmeserver.repository;

import explorewithmeserver.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("from Request r " +
            "where r.event.initiator.id = ?1 " +
            "and r.event.id = ?2")
    List<Request> findRequestsByRequesterIdAndEventId(Long userId, Long eventId);

    @Query("from Request r " +
            "where r.event.initiator.id = ?1 " +
            "and r.event.id = ?2 " +
            "and r.id = ?3")
    Request findRequestByUserIdAndEventIdAndReqId(Long userId, Long eventId, Long reqId);

    @Query("from Request r where r.id = ?1 ")
    Request findRequestByUserId(Long reqId);

    @Query("from Request r where r.requester.id = ?1")
    List<Request> findAllRequestsByUserId(Long userId);
}
