package t1;

import com.lottomatching.domain.Lotto;
import com.lottomatching.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T5 {

    public static void main(String[] args) {
        List<Lotto> lottoList = new ArrayList<Lotto>();
        Lotto lotto = new Lotto();
        lotto.setNumber("4594");lotto.setGroup("11");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4594");lotto.setGroup("12");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4594");lotto.setGroup("13");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4594");lotto.setGroup("14");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4594");lotto.setGroup("15");lottoList.add(lotto);lotto = new Lotto();

        lotto.setNumber("4722");lotto.setGroup("66");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4722");lotto.setGroup("67");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4722");lotto.setGroup("68");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4722");lotto.setGroup("69");lottoList.add(lotto);lotto = new Lotto();

        lotto.setNumber("4820");lotto.setGroup("01");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4820");lotto.setGroup("02");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("4820");lotto.setGroup("03");lottoList.add(lotto);lotto = new Lotto();

        lotto.setNumber("8530");lotto.setGroup("77");lottoList.add(lotto);lotto = new Lotto();
        lotto.setNumber("8530");lotto.setGroup("78");lottoList.add(lotto);lotto = new Lotto();
        //lottoList.forEach(System.out::println);



        Map<String,List<Lotto>> lottoMap = new HashMap<String,List<Lotto>>();
        for(Lotto l: lottoList) {
            lottoMap.put(l.getNumber(),new ArrayList<Lotto>());
        }
        for (Map.Entry map : lottoMap.entrySet()) {
            List<Lotto> lottoArrayList = new ArrayList<Lotto>();
            for(Lotto l: lottoList) {
                if(map.getKey().equals(l.getNumber())){
                    lottoArrayList.add(l);
                    map.setValue(lottoArrayList);
                }

            }
        }
        for (Map.Entry map : lottoMap.entrySet()) {
            List<Lotto> mapValue =  (List<Lotto>)map.getValue();
            System.out.println("Key: "+map.getKey() + " & Value: " +mapValue.size());
        }

    }
}
