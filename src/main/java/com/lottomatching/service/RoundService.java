package com.lottomatching.service;

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
}
