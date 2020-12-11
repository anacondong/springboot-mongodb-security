package com.lottomatching.service;

import com.lottomatching.domain.News;
import com.lottomatching.repository.NewsRepository;
import com.lottomatching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public News findNewsById(Long id){
        return newsRepository.findById(id).get();
    }
}
