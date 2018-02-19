package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.City;
import com.frank.api.service.setup.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class CityController extends RestBaseController {

    @Autowired
    private CityService cityService;
    
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/cities/paginated")
    public Page<City> getPaginatedCities(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return cityService.getPaginatedCities(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cities")
    public Page<City> createCity(@Valid @RequestBody City city, @RequestParam("perPage") Integer perPage) {
        cityService.createCity(city);
        return cityService.getPaginatedCities(0,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable(value = "id") Long id) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(city);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/cities/{id}")
    public Page<City> updateCity(@PathVariable(value = "id") Long id, @Valid @RequestBody City cityDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        City city = cityService.getCityById(id);
        city.setName(cityDetails.getName());
        cityService.updateCity(city);
        return cityService.getPaginatedCities(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/cities/{id}")
    public Page<City> deleteCity(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        City city = cityService.getCityById(id);
        cityService.deleteCity(city);
        return cityService.getPaginatedCities(page,perPage);
    }
}