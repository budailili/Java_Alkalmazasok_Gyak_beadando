package hu.nje.autokonyv.repository;

import hu.nje.autokonyv.entity.Jarmu;
import org.springframework.data.repository.CrudRepository;

public interface JarmuRepository extends CrudRepository<Jarmu, String> {

    Iterable<Jarmu> findAllByOrderByMarkaAscRndszmAsc();
}
