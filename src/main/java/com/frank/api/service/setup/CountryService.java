package com.frank.api.service.setup;

import com.frank.api.model.setup.Country;
import com.frank.api.repository.setup.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country getCountryById(Long id){
        return countryRepository.findOne(id);
    }

    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Page<Country> getPaginatedCountries(Integer page, Integer perPage){
        return countryRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }
}
