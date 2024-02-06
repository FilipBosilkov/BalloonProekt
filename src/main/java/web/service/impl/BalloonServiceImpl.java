package web.service.impl;

import web.exceptions.BalloonNotFoundException;
import web.exceptions.ManufacturerNotFoundException;
import web.model.Balloon;
import web.model.Manufacturer;
import web.repsitory.BalloonRepository;
import web.repsitory.ManufacturerRepository;
import web.service.BalloonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BalloonServiceImpl implements BalloonService {

    private final BalloonRepository balloonRepository;
    private final ManufacturerRepository manufacturerRepository;

    public BalloonServiceImpl(BalloonRepository balloonRepository, ManufacturerRepository manufacturerRepository) {
        this.balloonRepository = balloonRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Balloon> listAll() {
        return balloonRepository.findAll();
    }

    @Override
    public List<Balloon> searchByNameOrDescription(String text) {
        return this.balloonRepository.findAllByNameOrDescription(text,text);
    }


    @Override
    public Optional<Balloon> findByName(String name) {
        return balloonRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Balloon> edit(Long id, String name, String description, Long manufacturerId) {

        Balloon balloon = this.balloonRepository.findById(id).orElseThrow(() -> new BalloonNotFoundException(id));

        balloon.setName(name);
        balloon.setDescription(description);


        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        balloon.setManufacturer(manufacturer);

        return Optional.of(this.balloonRepository.save(balloon));

    }

    @Override
    public Optional<Balloon> findById(Long id) {
        return balloonRepository.findById(id);
    }

    @Override
    public Optional<Balloon> save(String name, String description, Long manufacturerId) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId).orElseThrow(()-> new ManufacturerNotFoundException(manufacturerId));

        this.balloonRepository.deleteByName(name);
        return Optional.of(this.balloonRepository.save(new Balloon(name,description,manufacturer)));
    }

    @Override
    public void deleteById(Long id) {
        this.balloonRepository.deleteById(id);
    }


}
