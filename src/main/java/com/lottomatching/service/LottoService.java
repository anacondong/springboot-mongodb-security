package com.lottomatching.service;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
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

    public int save(Lotto lotto) {
        try {
            lottoRepository.save(lotto);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
