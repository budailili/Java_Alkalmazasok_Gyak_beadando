package hu.nje.autokonyv.repository;

import hu.nje.autokonyv.entity.Uzenet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UzenetRepository extends CrudRepository<Uzenet, Long> {

    // a legfrissebb üzenet legyen elöl
    List<Uzenet> findAllByOrderByKuldesIdejeDesc();
}
