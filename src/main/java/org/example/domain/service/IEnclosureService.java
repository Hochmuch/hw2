package org.example.domain.service;

import org.example.domain.Animal;
import org.example.domain.Enclosure;
import org.example.domain.valueobject.EnclosureType;

import java.util.List;
import java.util.Optional;

public interface IEnclosureService {

    List<Enclosure> getAllEnclosures();

    Optional<Enclosure> findEnclosureById(long id);

    List<Enclosure> getEnclosuresByType(EnclosureType type);

    Enclosure createEnclosure(Enclosure enclosure);

    Enclosure updateEnclosure(long id, Enclosure enclosureDetails);

    void deleteEnclosure(long id);


    boolean addAnimalToEnclosure(Enclosure enclosure, Animal animal);

    boolean removeAnimalFromEnclosure(Enclosure enclosure, Animal animal);

    String cleanEnclosure(long id);
} 