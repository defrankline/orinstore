package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Country;
import com.frank.api.service.setup.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class CountryController extends RestBaseController {

    @Autowired
    private CountryService countryService;
    
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/countries/paginated")
    public Page<Country> getPaginatedCountries(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return countryService.getPaginatedCountries(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/countries")
    public Page<Country> createCountry(@Valid @RequestBody Country country, @RequestParam("perPage") Integer perPage) {
        countryService.createCountry(country);
        return countryService.getPaginatedCountries(0,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") Long id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(country);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/countries/{id}")
    public Page<Country> updateCountry(@PathVariable(value = "id") Long id, @Valid @RequestBody Country countryDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Country country = countryService.getCountryById(id);
        country.setName(countryDetails.getName());
        countryService.updateCountry(country);
        return countryService.getPaginatedCountries(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/countries/{id}")
    public Page<Country> deleteCountry(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Country country = countryService.getCountryById(id);
        countryService.deleteCountry(country);
        return countryService.getPaginatedCountries(page,perPage);
    }

}