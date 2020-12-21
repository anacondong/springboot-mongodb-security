package com.lottomatching.service;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.repository.LottoRepository;
import com.lottomatching.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LottoService {

    @Autowired
    private LottoRepository lottoRepository;

    public List<Lotto> findByUserOrderByIdDesc(User user) {
        return lottoRepository.findByUserOrderByIdDesc(user);
    }

    public Lotto findByBarcode(String barcode) {
        return lottoRepository.findByBarcode(barcode);
    }

    public List<Lotto> findByRound(String roundNumber) {
        return lottoRepository.findByRound(roundNumber);
    }

    public int save(Lotto lotto) {
        try {
            lottoRepository.save(lotto);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int saveAll(List<Lotto> lottoList) {
        int count = 0;
        for(Lotto lotto: lottoList){
            try {
                lottoRepository.save(lotto);
                count++;
            } catch (Exception e) {

            }
        }
        return count;
    }

    public List<Lotto> findByUserAndRoundOrderByIdDesc(User user, String roundNumber) {
        return lottoRepository.findByUserAndRoundOrderByIdDesc(user, roundNumber);
    }

    public List<Lotto> findByUserAndRoundAndEnabledOrderByIdDesc(User user, String roundNumber,boolean enable) {
        return lottoRepository.findByUserAndRoundAndEnabledOrderByIdDesc(user, roundNumber, enable);
    }

    public List<Lotto> findByRoundAndEnabledOrderByIdDesc(String roundNumber, boolean enabled) {
        return lottoRepository.findByRoundAndEnabledOrderByIdDesc(roundNumber, enabled);
    }

    public List<Lotto> findByUserAndRoundAndMatch(User user, String roundNumber, boolean isMatch) {
        return lottoRepository.findByUserAndRoundAndMatch(user, roundNumber, isMatch);
    }


}
