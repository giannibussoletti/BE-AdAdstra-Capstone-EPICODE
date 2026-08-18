package adastra.backend.specifications;

import adastra.backend.entities.Screen;
import adastra.backend.entities.ScreeningTime;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ScreeningTimeSpecs {

    public static Specification<ScreeningTime> filterByCinema(UUID cinemaId) {
        return ((root, query, criteriaBuilder) -> {
            if (cinemaId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<ScreeningTime, Screen> screenJoin = root.join("screenId");

            return criteriaBuilder.equal(screenJoin.get("cinemaId").get("id"), cinemaId);
        });
    }

//    public static Specification<ScreeningTime> filterByMovie(UUID movieId) {
//        return ((root, query, criteriaBuilder) -> {
//            if (movieId == null) {
//                return criteriaBuilder.conjunction();
//            }
//
//            return criteriaBuilder.equal(root.get("movieId").get("Id"), movieId);
//        });
//    }
}
