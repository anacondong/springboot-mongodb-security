package t1;

import com.lottomatching.domain.Lotto;
import com.lottomatching.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T3 {

    public static void main(String[] args) {
        // user matching
        DecimalFormat barcodeDf = new DecimalFormat("0000000000000000");
        DecimalFormat numberDf = new DecimalFormat("0000");
        DecimalFormat groupDf = new DecimalFormat("00");
        DecimalFormat roundDf = new DecimalFormat("00");

        //random all match
        List<Lotto> lottosAll = new ArrayList<Lotto>();
        for(int i =0; i <= 10000; i++){
            Lotto l = new Lotto ();
            l.setBarcode(barcodeDf.format(i));
            l.setNumber(numberDf.format((Math.random() * 1000) + 1));
            l.setGroup(groupDf.format((Math.random() * 100) + 1));
            l.setRound("35");
            lottosAll.add(l);
        }
        // lottosAll.stream().forEach(l -> System.out.println("<<< lottosAllMatch >>> barcode: "+l.getBarcode()+" number: "+l.getNumber()+" group: "+l.getGroup()+" round: "+l.getRound()));

        // random user lotto list
        List<Lotto> userLotto = new ArrayList<Lotto>();
        for(int i =1; i <= 10 ; i++){
            Lotto l = lottosAll.get(i);
            l.setNumber(numberDf.format((Math.random() * 1000) + 1));
            l.setGroup(groupDf.format((Math.random() * 10) + 1));
            userLotto.add(l);
        }
        // userLotto.stream().forEach(l -> System.out.println("<<< lottos >>> barcode: "+l.getBarcode()+" number: "+l.getNumber()+" group: "+l.getGroup()+" round: "+l.getRound()));

        // same round >> same lotto number, 1-5 group
        Map groupMap = Utils.getGroupMap();
        List<Lotto> matched = new ArrayList<Lotto>();
        int count = 1;
        for(Lotto ul: userLotto){
            for(Lotto am: lottosAll){
                if(ul.getRound().equals(am.getRound())){ // round
                    if(ul.getNumber().equals(am.getNumber())){ // number
                        if(groupMap.get(ul.getGroup()).equals(groupMap.get(am.getGroup()))){
                            System.out.println("====="+count+"=====");
                            System.out.println("User's lotto            : "+ul.toString());
                            System.out.println("All lotto in the system : "+am.toString());
                            count++;
                        }
                    }
                }
            }
        }
    }


}





//        <<< lottos >>> barcode: 1933265490549050 number: 5490 group: 26 round: 33
//        <<< lottos >>> barcode: 1933109360936050 number: 9360 group: 10 round: 33
//        <<< lottos >>> barcode: 1933060840084050 number: 0840 group: 06 round: 33
//        <<< lottos >>> barcode: 1933290830083050 number: 0830 group: 29 round: 33
//        <<< lottos >>> barcode: 1933291280128050 number: 1280 group: 29 round: 33
//        <<< lottos >>> barcode: 1933015697569750 number: 5697 group: 01 round: 33
//        <<< lottos >>> barcode: 1933268337833750 number: 8337 group: 26 round: 33
//        <<< lottos >>> barcode: 1933025487548750 number: 5487 group: 02 round: 33
//        <<< lottos >>> barcode: 1933067717771750 number: 7717 group: 06 round: 33
//        <<< lottos >>> barcode: 1933262567256750 number: 2567 group: 26 round: 33
//        <<< lottos >>> barcode: 1933015607560750 number: 5607 group: 01 round: 33
//        <<< lottos >>> barcode: 1933279557955750 number: 9557 group: 27 round: 33
//        <<< lottos >>> barcode: 1933061037103750 number: 1037 group: 06 round: 33
//        <<< lottos >>> barcode: 1933298097809750 number: 8097 group: 29 round: 33
//        <<< lottos >>> barcode: 1933296829682950 number: 6829 group: 29 round: 33
//        <<< lottos >>> barcode: 1933013666366650 number: 3666 group: 01 round: 33
//        <<< lottos >>> barcode: 1933024036403650 number: 4036 group: 02 round: 33
//        <<< lottos >>> barcode: 1933020936093650 number: 0936 group: 02 round: 33
//        <<< lottos >>> barcode: 1933013776377650 number: 3776 group: 01 round: 33
//        <<< lottos >>> barcode: 1933299536953650 number: 9536 group: 29 round: 33
//        <<< lottos >>> barcode: 1933059626962650 number: 9626 group: 05 round: 33
//        <<< lottos >>> barcode: 1933263516351650 number: 3516 group: 26 round: 33
//        <<< lottos >>> barcode: 1933140826082650 number: 0826 group: 14 round: 33
//        <<< lottos >>> barcode: 1933017246724650 number: 7246 group: 01 round: 33
//        <<< lottos >>> barcode: 1933083289328950 number: 3289 group: 08 round: 33
//        <<< lottos >>> barcode: 1933291456145650 number: 1456 group: 29 round: 33
//        <<< lottos >>> barcode: 1933297416741650 number: 7416 group: 29 round: 33
//        <<< lottos >>> barcode: 1933264696469650 number: 4696 group: 26 round: 33
//        <<< lottos >>> barcode: 1933065646564650 number: 5646 group: 06 round: 33
//        <<< lottos >>> barcode: 1933296366636650 number: 6366 group: 29 round: 33
//        <<< lottos >>> barcode: 1933046106610650 number: 6106 group: 04 round: 33
//        <<< lottos >>> barcode: 1933106116611650 number: 6116 group: 10 round: 33
//        <<< lottos >>> barcode: 1933290036003650 number: 0036 group: 29 round: 33
//        <<< lottos >>> barcode: 1933292266226650 number: 2266 group: 29 round: 33
//        <<< lottos >>> barcode: 1933029049904950 number: 9049 group: 02 round: 33
//        <<< lottos >>> barcode: 1933260115011550 number: 0115 group: 26 round: 33
//        <<< lottos >>> barcode: 1933082345234550 number: 2345 group: 08 round: 33
//        <<< lottos >>> barcode: 1933295685568550 number: 5685 group: 29 round: 33
//        <<< lottos >>> barcode: 1933076475647550 number: 6475 group: 07 round: 33
//        <<< lottos >>> barcode: 1933065975597550 number: 5975 group: 06 round: 33
//        <<< lottos >>> barcode: 1933294895489550 number: 4895 group: 29 round: 33
//        <<< lottos >>> barcode: 1933295215521550 number: 5215 group: 29 round: 33
//        <<< lottos >>> barcode: 1933247785778550 number: 7785 group: 24 round: 33
//        <<< lottos >>> barcode: 1933270945094550 number: 0945 group: 27 round: 33
//        <<< lottos >>> barcode: 1933053179317950 number: 3179 group: 05 round: 33
//        <<< lottos >>> barcode: 1933295810581050 number: 5810 group: 29 round: 33
//        <<< lottos >>> barcode: 1933025280528050 number: 5280 group: 02 round: 33
//        <<< lottos >>> barcode: 1933299470947050 number: 9470 group: 29 round: 33
//        <<< lottos >>> barcode: 1933012310231050 number: 2310 group: 01 round: 33
//        <<< lottos >>> barcode: 1933241840184050 number: 1840 group: 24 round: 33
//        <<< lottos >>> barcode: 1933297543754350 number: 7543 group: 29 round: 33
//        <<< lottos >>> barcode: 1933065623562350 number: 5623 group: 06 round: 33
//        <<< lottos >>> barcode: 1933299203920350 number: 9203 group: 29 round: 33
//        <<< lottos >>> barcode: 1933270503050350 number: 0503 group: 27 round: 33
//        <<< lottos >>> barcode: 1933011838183850 number: 1838 group: 01 round: 33
//        <<< lottos >>> barcode: 1933299168916850 number: 9168 group: 29 round: 33
//        <<< lottos >>> barcode: 1933292111211150 number: 2111 group: 29 round: 33
//        <<< lottos >>> barcode: 1933297921792150 number: 7921 group: 29 round: 33
//        <<< lottos >>> barcode: 1933027341734150 number: 7341 group: 02 round: 33
//        <<< lottos >>> barcode: 1933016428642850 number: 6428 group: 01 round: 33
//        <<< lottos >>> barcode: 1933269886988650 number: 9886 group: 26 round: 33
//        <<< lottos >>> barcode: 1933264671467150 number: 4671 group: 26 round: 33
//        <<< lottos >>> barcode: 1933268545854550 number: 8545 group: 26 round: 33
//        <<< lottos >>> barcode: 1933263730373050 number: 3730 group: 26 round: 33
//        <<< lottos >>> barcode: 1933267404740450 number: 7404 group: 26 round: 33
//        <<< lottos >>> barcode: 1933294321432150 number: 4321 group: 29 round: 33
//        <<< lottos >>> barcode: 1933277631763150 number: 7631 group: 27 round: 33
//        <<< lottos >>> barcode: 1933272511251150 number: 2511 group: 27 round: 33
//        <<< lottos >>> barcode: 1933022721272150 number: 2721 group: 02 round: 33
//        <<< lottos >>> barcode: 1933135101510150 number: 5101 group: 13 round: 33
//        <<< lottos >>> barcode: 1933295201520150 number: 5201 group: 29 round: 33
//        <<< lottos >>> barcode: 1933084841484150 number: 4841 group: 08 round: 33
//        <<< lottos >>> barcode: 1933241875187550 number: 1875 group: 24 round: 33
//        <<< lottos >>> barcode: 1933298195819550 number: 8195 group: 29 round: 33
//        <<< lottos >>> barcode: 1933017975797550 number: 7975 group: 01 round: 33
//        <<< lottos >>> barcode: 1933290635063550 number: 0635 group: 29 round: 33
//        <<< lottos >>> barcode: 1933021504150450 number: 1504 group: 02 round: 33
//        <<< lottos >>> barcode: 1933269844984450 number: 9844 group: 26 round: 33
//        <<< lottos >>> barcode: 1933264934493450 number: 4934 group: 26 round: 33
//        <<< lottos >>> barcode: 1933025480548050 number: 5480 group: 02 round: 33
//        <<< lottos >>> barcode: 1933109899989950 number: 9899 group: 10 round: 33
//        <<< lottos >>> barcode: 1933279771977150 number: 9771 group: 27 round: 33
//        <<< lottos >>> barcode: 1933015640564050 number: 5640 group: 01 round: 33
//        <<< lottos >>> barcode: 1933027560756050 number: 7560 group: 02 round: 33
//        <<< lottos >>> barcode: 1933293120312050 number: 3120 group: 29 round: 33
//        <<< lottos >>> barcode: 1933298402840250 number: 8402 group: 29 round: 33
//        <<< lottos >>> barcode: 1933274062406250 number: 4062 group: 27 round: 33
//        <<< lottos >>> barcode: 1933018152815250 number: 8152 group: 01 round: 33
//        <<< lottos >>> barcode: 1933174372437250 number: 4372 group: 17 round: 33
//        <<< lottos >>> barcode: 1933292962296250 number: 2962 group: 29 round: 33
//        <<< lottos >>> barcode: 1933028792879250 number: 8792 group: 02 round: 33
//        <<< lottos >>> barcode: 1933263062306250 number: 3062 group: 26 round: 33
//        <<< lottos >>> barcode: 1933014722472250 number: 4722 group: 01 round: 33
//        <<< lottos >>> barcode: 1933028530853050 number: 8530 group: 02 round: 33
//        <<< lottos >>> barcode: 1933295265526550 number: 5265 group: 29 round: 33
//        <<< lottos >>> barcode: 1933294704470450 number: 4704 group: 29 round: 33
//        <<< lottos >>> barcode: 1933272644264450 number: 2644 group: 27 round: 33
//        <<< lottos >>> barcode: 1933299064906450 number: 9064 group: 29 round: 33
//        <<< lottos >>> barcode: 1933020184018450 number: 0184 group: 02 round: 33
//        <<< lottos >>> barcode: 1933055494549450 number: 5494 group: 05 round: 33
//        <<< lottos >>> barcode: 1933104594459450 number: 4594 group: 10 round: 33
//        <<< lottos >>> barcode: 1933024820482050 number: 4820 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933265490549050 number: 5490 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933109360936050 number: 9360 group: 10 round: 33
//        <<< lottosAllMatch >>> barcode: 1933060840084050 number: 0840 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933290830083050 number: 0830 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933291280128050 number: 1280 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933015697569750 number: 5697 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933268337833750 number: 8337 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933025487548750 number: 5487 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933067717771750 number: 7717 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933262567256750 number: 2567 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933015607560750 number: 5607 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933279557955750 number: 9557 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933061037103750 number: 1037 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933298097809750 number: 8097 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933296829682950 number: 6829 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933013666366650 number: 3666 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933024036403650 number: 4036 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933020936093650 number: 0936 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933013776377650 number: 3776 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933299536953650 number: 9536 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933059626962650 number: 9626 group: 05 round: 33
//        <<< lottosAllMatch >>> barcode: 1933263516351650 number: 3516 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933140826082650 number: 0826 group: 14 round: 33
//        <<< lottosAllMatch >>> barcode: 1933017246724650 number: 7246 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933083289328950 number: 3289 group: 08 round: 33
//        <<< lottosAllMatch >>> barcode: 1933291456145650 number: 1456 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933297416741650 number: 7416 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933264696469650 number: 4696 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933065646564650 number: 5646 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933296366636650 number: 6366 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933046106610650 number: 6106 group: 04 round: 33
//        <<< lottosAllMatch >>> barcode: 1933106116611650 number: 6116 group: 10 round: 33
//        <<< lottosAllMatch >>> barcode: 1933290036003650 number: 0036 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933292266226650 number: 2266 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933029049904950 number: 9049 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933260115011550 number: 0115 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933082345234550 number: 2345 group: 08 round: 33
//        <<< lottosAllMatch >>> barcode: 1933295685568550 number: 5685 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933076475647550 number: 6475 group: 07 round: 33
//        <<< lottosAllMatch >>> barcode: 1933065975597550 number: 5975 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933294895489550 number: 4895 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933295215521550 number: 5215 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933247785778550 number: 7785 group: 24 round: 33
//        <<< lottosAllMatch >>> barcode: 1933270945094550 number: 0945 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933053179317950 number: 3179 group: 05 round: 33
//        <<< lottosAllMatch >>> barcode: 1933295810581050 number: 5810 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933025280528050 number: 5280 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933299470947050 number: 9470 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933012310231050 number: 2310 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933241840184050 number: 1840 group: 24 round: 33
//        <<< lottosAllMatch >>> barcode: 1933297543754350 number: 7543 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933065623562350 number: 5623 group: 06 round: 33
//        <<< lottosAllMatch >>> barcode: 1933299203920350 number: 9203 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933270503050350 number: 0503 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933011838183850 number: 1838 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933299168916850 number: 9168 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933292111211150 number: 2111 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933297921792150 number: 7921 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933027341734150 number: 7341 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933016428642850 number: 6428 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933269886988650 number: 9886 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933264671467150 number: 4671 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933268545854550 number: 8545 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933263730373050 number: 3730 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933267404740450 number: 7404 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933294321432150 number: 4321 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933277631763150 number: 7631 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933272511251150 number: 2511 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933022721272150 number: 2721 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933135101510150 number: 5101 group: 13 round: 33
//        <<< lottosAllMatch >>> barcode: 1933295201520150 number: 5201 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933084841484150 number: 4841 group: 08 round: 33
//        <<< lottosAllMatch >>> barcode: 1933241875187550 number: 1875 group: 24 round: 33
//        <<< lottosAllMatch >>> barcode: 1933298195819550 number: 8195 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933017975797550 number: 7975 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933290635063550 number: 0635 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933021504150450 number: 1504 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933269844984450 number: 9844 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933264934493450 number: 4934 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933025480548050 number: 5480 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933109899989950 number: 9899 group: 10 round: 33
//        <<< lottosAllMatch >>> barcode: 1933279771977150 number: 9771 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933015640564050 number: 5640 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933027560756050 number: 7560 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933293120312050 number: 3120 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933298402840250 number: 8402 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933274062406250 number: 4062 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933018152815250 number: 8152 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933174372437250 number: 4372 group: 17 round: 33
//        <<< lottosAllMatch >>> barcode: 1933292962296250 number: 2962 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933028792879250 number: 8792 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933263062306250 number: 3062 group: 26 round: 33
//        <<< lottosAllMatch >>> barcode: 1933014722472250 number: 4722 group: 01 round: 33
//        <<< lottosAllMatch >>> barcode: 1933028530853050 number: 8530 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933295265526550 number: 5265 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933294704470450 number: 4704 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933272644264450 number: 2644 group: 27 round: 33
//        <<< lottosAllMatch >>> barcode: 1933299064906450 number: 9064 group: 29 round: 33
//        <<< lottosAllMatch >>> barcode: 1933020184018450 number: 0184 group: 02 round: 33
//        <<< lottosAllMatch >>> barcode: 1933055494549450 number: 5494 group: 05 round: 33
//        <<< lottosAllMatch >>> barcode: 1933104594459450 number: 4594 group: 10 round: 33
//        <<< lottosAllMatch >>> barcode: 1933024820482050 number: 4820 group: 02 round: 33



