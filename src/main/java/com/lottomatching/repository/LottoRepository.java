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
    List<Lotto> findByUserAndRoundAndEnabledOrderByIdDesc(User user, String roundNumber,boolean enabled);
    List<Lotto> findByRoundAndEnabledOrderByIdDesc(String roundNumber,boolean enabled);
    Lotto findByBarcode(String barcode);
    List<Lotto> findByRound(String roundNumber);
    List<Lotto> findByUserAndRoundAndMatch(User user, String roundNumber,boolean isMatch);
    List<Lotto> findByUserAndRoundAndReceived(User user, String roundNumber,boolean isReceived);
    List<Lotto> findByRoundAndMatch(String roundNumber,boolean isMatch);

}
