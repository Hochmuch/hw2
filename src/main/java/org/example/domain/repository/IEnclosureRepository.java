package org.example.domain.repository;

import org.example.domain.Enclosure;
import org.example.domain.valueobject.EnclosureType;

import java.util.List;
import java.util.Optional;

public interface IEnclosureRepository {

    List<Enclosure> findAll();

    Optional<Enclosure> findById(long id);

    List<Enclosure> findByType(EnclosureType type);

    Enclosure save(Enclosure enclosure);

    void deleteById(long id);

    boolean existsById(long id);
}