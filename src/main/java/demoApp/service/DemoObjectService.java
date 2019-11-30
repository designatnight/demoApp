package demoApp.service;

import demoApp.dao.DemoObjectRepository;
import demoApp.domain.DemoObject;
import demoApp.exception.DemoObjectNotFoundException;
import demoApp.exception.NonUniqueNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DemoObjectService {

    @Autowired
    DemoObjectRepository demoObjectRepository;

    public DemoObjectService(DemoObjectRepository demoObjectRepository) {
        this.demoObjectRepository = demoObjectRepository;
    }

    public DemoObject getDemoObjectById(UUID id) {
        Optional<DemoObject> response = demoObjectRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new DemoObjectNotFoundException("Object not found ID: " + id );
        }
    }

    public List<DemoObject> getAllDemoObjects() {
        List<DemoObject> demoObjects = new ArrayList<>();
        demoObjectRepository.findAll().forEach(demoObject -> demoObjects.add(demoObject));
        return demoObjects;
    }

    public DemoObject createDemoObject(DemoObject demoObject) {
       return saveDemoObject(demoObject);
    }

    public DemoObject updateDemoObject(DemoObject demoObject) {
        return saveDemoObject(demoObject);
    }

    public void deleteDemoObject(UUID id) {
        demoObjectRepository.deleteById(id);
    }

    private DemoObject saveDemoObject(DemoObject demoObject) {
        try {
            return demoObjectRepository.save(demoObject);
        } catch (DataIntegrityViolationException ex) {
            throw new NonUniqueNameException("Name must be unique");
        }
    }

}
