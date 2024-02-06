package web.service;

import web.model.Balloon;

import java.util.List;
import java.util.Optional;

public interface BalloonService {
    List<Balloon> listAll();
    List<Balloon> searchByNameOrDescription(String text);

    Optional<Balloon> findById(Long id);

    Optional<Balloon> save(String name, String description, Long manufacturerId);
    void deleteById(Long id);
    Optional<Balloon> findByName(String name);

    Optional<Balloon> edit(Long id, String name, String description, Long manufacturerId);
}
