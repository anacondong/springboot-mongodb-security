package com.lottomatching.service;

import com.lottomatching.domain.Delivery;
import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.User;
import com.lottomatching.repository.DeliveryRepository;
import com.lottomatching.repository.RoundRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery findById(ObjectId id){
        return deliveryRepository.findById(id);
    }

    public List<Delivery> findByRound(String roundNumber){
        return deliveryRepository.findByRound(roundNumber);
    }

    public List<Delivery> findByRoundOrderByStatusDesc(String roundNumber){
        return deliveryRepository.findByRoundOrderByStatusDesc(roundNumber);
    }

    public List<Delivery> findByUserAndRound(User user, String roundNumber){
        return deliveryRepository.findByUserAndRound(user, roundNumber);
    }

    public int saveAll(List<Delivery> deliveryList) {
        int count = 0;
        for(Delivery delivery: deliveryList){
            try {
                deliveryRepository.save(delivery);
                count++;
            } catch (Exception e) {

            }
        }
        return count;
    }

    public boolean save(Delivery delivery) {
        boolean result = true;
        try {
            deliveryRepository.save(delivery);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
