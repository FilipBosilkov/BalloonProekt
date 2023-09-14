package web.service;

import web.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService{
    public List<Manufacturer> findAll();

    public Optional<Manufacturer> findById(Long id);
    Optional<Manufacturer> save(String name, String country,String address);
    void deleteById(Long id);

}
