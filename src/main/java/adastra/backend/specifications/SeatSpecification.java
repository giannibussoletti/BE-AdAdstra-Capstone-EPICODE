package adastra.backend.specifications;

import adastra.backend.entities.Seat;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SeatSpecification {

    public static Specification<Seat> filterByScreen(UUID screenId) {
        return ((root, query, criteriaBuilder) -> {
            if (screenId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("screenId").get("id"), screenId);
        });
    }

    public static Specification<Seat> filterByCinema(UUID cinemaId) {
        return ((root, query, criteriaBuilder) -> {
            if (cinemaId == null) {
                return criteriaBuilder.conjunction();
            }


            return criteriaBuilder.equal(root.get("screenId").get("cinemaId").get("id"), cinemaId);
        });
    }

    public static Specification<Seat> filterByRow(Character row) {
        return ((root, query, criteriaBuilder) -> {
            if (row == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("row"), row);
        });

    }

    public static Specification<Seat> filterByNumber(Integer number) {
        return ((root, query, criteriaBuilder) -> {
            if (number == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("number"), number);
        });
    }

}
