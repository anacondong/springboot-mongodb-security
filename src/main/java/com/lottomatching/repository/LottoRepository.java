package com.lottomatching.repository;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LottoRepository extends MongoRepository<Lotto, Long> {
    List<Lotto> findByUserOrderByIdDesc(User user);
    List<Lotto> findByUserAndRoundOrderByIdDesc(User user, String roundNumber);


    Lotto findByBarcode(String barcode);
}
