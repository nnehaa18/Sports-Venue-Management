package com.assignment.service;

import com.assignment.dao.SportRepository;
import com.assignment.dto.SportApiDTO;
import com.assignment.dto.SportApiResponse;
import com.assignment.entities.Sport;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SportService {
    private final SportRepository sportRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String SPORTS_API = "https://stapubox.com/sportslist/";

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    /**
     * fetches sports from stapubox api
     * stores result in db
     */
    public void pullSports () {
        SportApiResponse response = restTemplate.getForObject(SPORTS_API, SportApiResponse.class);
        List<SportApiDTO> sportsList = response.getData();
//        sport codes fetched from api response
        Set<String> resultantSportCodes = sportsList.stream().map(SportApiDTO::getSportCode).collect(Collectors.toSet());
//        sport codes fetched from db
        Set<String> existingCodes = sportRepository.findBySportCodeIn(resultantSportCodes).stream()
                .map(Sport::getSportCode)
                .collect(Collectors.toSet());

//        inserts fresh entries, avoiding duplicates
        List<Sport> bulkInsertEntries = sportsList.stream()
                .filter(dto -> !existingCodes.contains(dto.getSportCode()))
                .map(dto -> {
                    Sport sport = new Sport();
                    sport.setSportCode(dto.getSportCode());
                    sport.setSportName(dto.getSportName());
                    return sport;
                }).toList();

        sportRepository.saveAll(bulkInsertEntries);
    }

    public Sport findSportById (Long id) {
        return sportRepository.getById(id);
    }
}
