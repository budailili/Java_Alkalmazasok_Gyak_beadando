package hu.nje.autokonyv.repository;

import hu.nje.autokonyv.entity.Szemely;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SzemelyRepository extends CrudRepository<Szemely, Integer> {

    List<Szemely> findAllByOrderByNevAsc();

    List<Szemely> findByRendszam(String rendszam);
}
