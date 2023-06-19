package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.converters.TourToTourDTOConverter;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.Country;
import com.digital.DigitaBooking.models.entities.Feature;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.models.dtos.TourDTO;
import com.digital.DigitaBooking.repositories.ICategoryRepository;
import com.digital.DigitaBooking.repositories.ICountryRepository;
import com.digital.DigitaBooking.repositories.IFeatureRepository;
import com.digital.DigitaBooking.repositories.ITourRepository;
import com.digital.DigitaBooking.services.ITourService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TourService implements ITourService {

    @Autowired
    private ITourRepository tourRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IFeatureRepository featureRepository;

    @Autowired
    private ICountryRepository countryRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TourToTourDTOConverter tourConverter;

    @Override
    public Tour saveTour(TourDTO tourDTO) {
        Tour tour = mapper.convertValue(tourDTO, Tour.class);
        Category category = categoryRepository.findById(tourDTO.getCategoryId()).get();
        Set<Feature> features = new HashSet<>();
        tour.setCategory(category);
        Country country = countryRepository.findById(tourDTO.getCountryId()).get();
        tour.setCountry(country);
        for (Long featureId : tourDTO.getFeaturesId()) {
            features.add(featureRepository.getReferenceById(featureId));
        }
        tour.setFeatures(features);
        Tour newTour = tourRepository.save(tour);
        return newTour;
    }

    @Override
    public TourDTO getTour(Long id) {
        Tour tour = tourRepository.findById(id).get();
        TourDTO tourDTO = null;
        tourDTO = tourConverter.convert(tour);
        return tourDTO;
    }

    @Override
    public void updateTour(Long id, TourDTO tourDTO) {

        Optional<Tour> optionalTour = tourRepository.findById(id).map(tour -> {
            System.out.println("entro al map");
            Set<Feature> features = new HashSet<>();
            tour.setTourName(tourDTO.getTourName());
            tour.setTourDescription(tourDTO.getTourDescription());
            tour.setTourClassification(tourDTO.getTourClassification());
            tour.setTourCapacity(tourDTO.getTourCapacity());
            tour.setTourPrice(tourDTO.getTourPrice());
            tour.setTourScore(tourDTO.getTourScore());
            tour.setCountry(countryRepository.getById(tourDTO.getCountryId()));
            System.out.println(tourDTO.getFeaturesId());
            for (Long featureId : tourDTO.getFeaturesId()) {
                System.out.println("Entro a los features");
                features.add(featureRepository.getReferenceById(featureId));
            }
            tour.setFeatures(features);
            return tour;
        });

    }

    @Override
    public void deleteTour(Long id) {

        tourRepository.deleteById(id);
    }

    @Override
    public Set<TourDTO> getTours() {
        List<Tour> tours = tourRepository.findAll();
        Set<TourDTO> toursDTO = new HashSet<>();
        for (Tour tour :
                tours) {
//            tour.addFeature();
            toursDTO.add(tourConverter.convert(tour));

        }
        return toursDTO;
    }

    @Override
    public Set<TourDTO> getToursByCategory(Integer id) {
        Set<TourDTO> toursDTO = new HashSet<>();
        List<Tour> tours = tourRepository.findAllByCategoryId(id);
        for (Tour tour :
                tours) {
            toursDTO.add(tourConverter.convert(tour));

        }
        return toursDTO;
    }

    @Override
    public Set<TourDTO> getToursByCountry(Integer id) {
        Set<TourDTO> toursDTO = new HashSet<>();
        List<Tour> tours = tourRepository.findAllToursByCountry(id);
        for (Tour tour :
                tours) {
            toursDTO.add(tourConverter.convert(tour));
        }
        return toursDTO;
    }
}

