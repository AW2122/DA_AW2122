package com.aw2122.finalactivity.apirest.models;

import com.aw2122.finalactivity.apirest.models.ReservationsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsDAO extends CrudRepository<ReservationsEntity, Integer> {
}
