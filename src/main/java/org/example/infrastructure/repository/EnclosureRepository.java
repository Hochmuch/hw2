package org.example.infrastructure.repository;

import org.example.domain.Enclosure;
import org.example.domain.repository.IEnclosureRepository;
import org.example.domain.valueobject.EnclosureType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EnclosureRepository implements IEnclosureRepository {
    private final Map<Long, Enclosure> enclosures = new ConcurrentHashMap<>();

    @Override
    public Enclosure save(Enclosure enclosure) {
        if (enclosure == null) {
            throw new IllegalArgumentException("Enclosure cannot be null");
        }
        
        enclosures.put(enclosure.getId(), enclosure);
        return enclosure;
    }
    
    public List<Enclosure> saveAll(List<Enclosure> enclosureList) {
        List<Enclosure> savedEnclosures = new ArrayList<>();
        for (Enclosure enclosure : enclosureList) {
            savedEnclosures.add(save(enclosure));
        }
        return savedEnclosures;
    }
    
    @Override
    public Optional<Enclosure> findById(long id) {
        return Optional.ofNullable(enclosures.get(id));
    }
    
    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(enclosures.values());
    }
    
    @Override
    public List<Enclosure> findByType(EnclosureType type) {
        return enclosures.values().stream()
                .filter(enclosure -> enclosure.getType().equals(type))
                .toList();
    }
    
    public List<Enclosure> findByType(String type) {
        return enclosures.values().stream()
                .filter(enclosure -> enclosure.getType().toString().equals(type))
                .toList();
    }
    
    public void delete(Enclosure enclosure) {
        if (enclosure != null) {
            enclosures.remove(enclosure.getId());
        }
    }
    
    @Override
    public void deleteById(long id) {
        enclosures.remove(id);
    }
    
    @Override
    public boolean existsById(long id) {
        return enclosures.containsKey(id);
    }
    
    public long count() {
        return enclosures.size();
    }
} 