package demoApp.dao;

import demoApp.domain.DemoObject;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DemoObjectRepository extends CrudRepository<DemoObject, UUID> {
}
