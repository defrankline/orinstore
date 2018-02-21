package com.frank.api.service.setup;

import com.frank.api.model.setup.City;
import com.frank.api.repository.setup.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public City getCityById(Long id){
        return cityRepository.findOne(id);
    }

    public City updateCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Page<City> getPaginatedCities(Integer page, Integer perPage){
        return cityRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteCity(City city){
        cityRepository.delete(city);
    }

    public List<City> findAllByCountryId(Long countryId) {
        return cityRepository.findAllByCountryId(countryId);
    }

    public City getCityByCode(String code){
        return cityRepository.findOneByCode(code);
    }
}
