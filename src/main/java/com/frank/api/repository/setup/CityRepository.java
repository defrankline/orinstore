package com.frank.api.repository.setup;

import com.frank.api.model.setup.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    public List<City> findAllByCountryId(Long countryId);
    public City findOneByCode(String code);
}