package web.web.controller;

import web.model.Balloon;
import web.model.Manufacturer;
import web.service.BalloonService;
import web.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = {"/","/balloons"})
public class BalloonController {

    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;

    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
    }


    @GetMapping
    public String getBalloonsPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty() )
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        List<Balloon> balloons = this.balloonService.listAll();
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("balloons",balloons);
        model.addAttribute("manufacturers",balloons);
        return "balloons";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteBalloon(@PathVariable Long id){
        this.balloonService.deleteById(id);
        return "redirect:/balloons";
    }


    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBalloonPage(Model model){
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers",manufacturers);
        return "addBalloon";
    }

    @GetMapping("/add-manufacturer-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addManufacturerPage(){
        return "addManufacturer";
    }


    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditBalloonPage(@PathVariable Long id, Model model){
        if (this.balloonService.findById(id).isPresent())
        {
            Balloon balloon = this.balloonService.findById(id).get();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers",manufacturers);
            model.addAttribute("balloon",balloon);
            return "addBalloon";
        }
        return "redirect:/balloons?error=BalloonNotFound";
    }

    @PostMapping("/add")
    public String saveBalloon(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Long manufacturerId){
        if (id!=null){
            this.balloonService.edit(id, name, description, manufacturerId);
        }else {
            this.balloonService.save(name,description,manufacturerId);
        }

        return "redirect:/balloons";
    }

    @PostMapping("/addManufacturer")
    public String saveBalloon(@RequestParam String name,
                              @RequestParam String country,
                              @RequestParam String address){
        if (!name.isEmpty() && !country.isEmpty() && !address.isEmpty()) {
            this.manufacturerService.save(name, country, address);
        }
        return "redirect:/balloons";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){
        return "access_denied";
    }
}
