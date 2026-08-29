package adastra.backend.repository;

import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Ticket;
import adastra.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findTicketByScreeningTimeId(ScreeningTime timeId);

    @Query("SELECT t FROM Ticket t JOIN t.bookingId bk JOIN t.screeningTimeId st JOIN st.movieId mv WHERE bk.userId = :user")
    List<Ticket> findMovieByUser(@Param("user") User user);

}
