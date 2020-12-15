package com.lottomatching.service;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.Round;
import com.lottomatching.repository.NewsRepository;
import com.lottomatching.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {

    @Autowired
    private RoundRepository roundRepository;

    public List<Round> findAll() {
        return roundRepository.findAll();
    }

    public Round findByNumber(String number) {
        return roundRepository.findByNumber(number);
    }

    public boolean save(Round round) {
        try {
            roundRepository.save(round);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
