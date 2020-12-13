package com.lottomatching.repository;

import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoundRepository extends MongoRepository<Round, Long> {
    User findByNumber(String number);
}
