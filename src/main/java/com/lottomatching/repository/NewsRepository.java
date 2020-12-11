package com.lottomatching.repository;

import com.lottomatching.domain.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, Long> {
    News findByMsg(String msg);
}
