package org.example.application;

import org.example.domain.Animal;
import org.example.domain.Enclosure;
import org.example.domain.repository.IEnclosureRepository;
import org.example.domain.service.IEnclosureService;
import org.example.domain.valueobject.EnclosureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnclosureService implements IEnclosureService {
    
    private final IEnclosureRepository enclosureRepository;
    
    @Autowired
    public EnclosureService(IEnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @Override
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepository.findAll();
    }

    @Override
    public Optional<Enclosure> findEnclosureById(long id) {
        return enclosureRepository.findById(id);
    }

    @Override
    public List<Enclosure> getEnclosuresByType(EnclosureType type) {
        return enclosureRepository.findByType(type);
    }

    @Override
    public Enclosure createEnclosure(Enclosure enclosure) {
        return enclosureRepository.save(enclosure);
    }

    @Override
    public Enclosure updateEnclosure(long id, Enclosure enclosureDetails) {
        Optional<Enclosure> enclosureOptional = enclosureRepository.findById(id);
        
        if (enclosureOptional.isEmpty()) {
            throw new IllegalArgumentException("Вольер с ID " + id + " не найден");
        }
        
        Enclosure existingEnclosure = enclosureOptional.get();

        if (enclosureDetails.getType() != null) {
            existingEnclosure.setType(enclosureDetails.getType());
        }
        if (enclosureDetails.getSize() > 0) {
            existingEnclosure.setSize(enclosureDetails.getSize());
        }
        if (enclosureDetails.getCapacity() > 0) {
            existingEnclosure.setCapacity(enclosureDetails.getCapacity());
        }
        
        return enclosureRepository.save(existingEnclosure);
    }

    @Override
    public void deleteEnclosure(long id) {
        Optional<Enclosure> enclosureOptional = enclosureRepository.findById(id);
        
        if (enclosureOptional.isEmpty()) {
            throw new IllegalArgumentException("Вольер с ID " + id + " не найден");
        }
        
        Enclosure enclosure = enclosureOptional.get();

        if (!enclosure.getAnimals().isEmpty()) {
            throw new IllegalArgumentException("Нельзя удалить вольер, содержащий животных");
        }
        
        enclosureRepository.deleteById(id);
    }

    @Override
    public boolean addAnimalToEnclosure(Enclosure enclosure, Animal animal) {
        if (enclosure.addAnimal(animal)) {
            enclosureRepository.save(enclosure);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAnimalFromEnclosure(Enclosure enclosure, Animal animal) {
        if (enclosure.removeAnimal(animal)) {
            enclosureRepository.save(enclosure);
            return true;
        }
        return false;
    }

    @Override
    public String cleanEnclosure(long id) {
        Optional<Enclosure> enclosureOptional = findEnclosureById(id);
        
        if (enclosureOptional.isEmpty()) {
            throw new IllegalArgumentException("Вольер с ID " + id + " не найден");
        }
        
        Enclosure enclosure = enclosureOptional.get();
        enclosure.clean();
        return "Вольер успешно очищен";
    }
} 