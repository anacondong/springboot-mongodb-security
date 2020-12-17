package com.lottomatching.repository;

import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoundRepository extends MongoRepository<Round, Long> {
    Round findByNumber(String number);

    List<Round> findByStatus(String status);
}
