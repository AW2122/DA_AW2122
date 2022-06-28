package com.aw2122.finalactivity.apirest.controllers;

import com.aw2122.finalactivity.apirest.models.ReservationsDAO;
import com.aw2122.finalactivity.apirest.models.ReservationsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aw2122-api-rest/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsDAO reservationsDAO;

    @GetMapping
    public List<ReservationsEntity> findAllReservations() {
        return (List<ReservationsEntity>) reservationsDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsEntity> findReservationById(@PathVariable(value = "id") int id) {
        Optional<ReservationsEntity> reservation = reservationsDAO.findById(id);

        if (reservation.isPresent()) {
            return ResponseEntity.ok().body(reservation.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ReservationsEntity saveReservation (@Validated @RequestBody ReservationsEntity reservation) {
        return reservationsDAO.save(reservation);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReservation (@PathVariable(value = "id") int id) {
        Optional<ReservationsEntity> reservation = reservationsDAO.findById(id);
        if (reservation.isPresent()) {
            reservationsDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
