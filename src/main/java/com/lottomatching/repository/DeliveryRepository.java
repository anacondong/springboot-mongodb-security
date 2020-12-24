package com.lottomatching.repository;

import com.lottomatching.domain.Delivery;
import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryRepository  extends MongoRepository<Delivery, Long> {

    Delivery findById(ObjectId id);
    List<Delivery> findByUser(User user);
    List<Delivery> findByRound(String roundNumber);
    List<Delivery> findByRoundOrderByStatusDesc(String roundNumber);
    List<Delivery> findByUserAndRound(User user, String roundNumber);
}
