package hu.nje.autokonyv.repository;

import hu.nje.autokonyv.entity.Telefon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TelefonRepository extends CrudRepository<Telefon, Integer> {

    List<Telefon> findBySzemelyid(Integer szemelyid);
}
