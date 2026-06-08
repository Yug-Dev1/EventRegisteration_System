package com.MiniProject.eventregistration.Service.specification;

import com.MiniProject.eventregistration.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EventSpecification {
    public static Specification<Event> titleContains(String title){
        return (root, query, criteriaBuilder) ->
                title ==null || title.isBlank()
                ?null
                        :criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                        "%"+ title.toLowerCase()+"%"
                );
    }
    public static Specification<Event> locationEquals(String location) {
        return (root, query, cb) ->
                location == null || location.isBlank()
                        ? null
                        : cb.equal(
                        cb.lower(root.get("location")),
                        location.toLowerCase()
                );
    }

    public static Specification<Event> ageEligible(Integer age) {
        return (root, query, cb) ->
                age == null
                        ? null
                        : cb.and(
                                cb.lessThanOrEqualTo(
                                        root.get("minAge"),
                                        age
                                ),

                        cb.greaterThanOrEqualTo(
                                root.get("maxAge"),
                                age
                        )
                );
    }

    public static Specification<Event> idIn(List<Long> ids) {
        return (root, query, cb) ->
                ids == null
                        ? null
                        : root.get("id").in(ids);
    }
}
