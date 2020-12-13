package t1;

import java.util.Arrays;
import java.util.List;

public class T1 {

    public static void main(String[] args) {

        String barcode = "1933024820482050";
        String barcodes = "1933024820482050\n" +
                "1933025480548050\n" +
                "1933104594459450\n" +
                "1933264934493450\n" +
                "1933269844984450\n" +
                "1933021504150450\n" +
                "1933055494549450\n" +
                "1933020184018450\n" +
                "1933299064906450\n" +
                "1933272644264450\n" +
                "1933294704470450\n" +
                "1933295265526550\n" +
                "1933028530853050\n" +
                "1933014722472250\n" +
                "1933263062306250\n" +
                "1933028792879250\n" +
                "1933292962296250\n" +
                "1933174372437250\n" +
                "1933018152815250\n" +
                "1933274062406250\n" +
                "1933298402840250\n" +
                "1933293120312050\n" +
                "1933027560756050\n" +
                "1933015640564050\n" +
                "1933279771977150\n" +
                "1933109899989950\n" +
                "1933290635063550\n" +
                "1933017975797550\n" +
                "1933298195819550\n" +
                "1933241875187550\n" +
                "1933084841484150\n" +
                "1933295201520150\n" +
                "1933135101510150\n" +
                "1933022721272150\n" +
                "1933272511251150\n" +
                "1933277631763150\n" +
                "1933294321432150\n" +
                "1933267404740450\n" +
                "1933263730373050\n" +
                "1933268545854550\n" +
                "1933264671467150\n" +
                "1933269886988650\n" +
                "1933016428642850\n" +
                "1933027341734150\n" +
                "1933297921792150\n" +
                "1933292111211150\n" +
                "1933299168916850\n" +
                "1933011838183850\n" +
                "1933270503050350\n" +
                "1933299203920350\n" +
                "1933065623562350\n" +
                "1933297543754350\n" +
                "1933241840184050\n" +
                "1933012310231050\n" +
                "1933299470947050\n" +
                "1933025280528050\n" +
                "1933295810581050\n" +
                "1933053179317950\n" +
                "1933270945094550\n" +
                "1933247785778550\n" +
                "1933295215521550\n" +
                "1933294895489550\n" +
                "1933065975597550\n" +
                "1933076475647550\n" +
                "1933295685568550\n" +
                "1933082345234550\n" +
                "1933260115011550\n" +
                "1933029049904950\n" +
                "1933292266226650\n" +
                "1933290036003650\n" +
                "1933106116611650\n" +
                "1933046106610650\n" +
                "1933296366636650\n" +
                "1933065646564650\n" +
                "1933264696469650\n" +
                "1933297416741650\n" +
                "1933291456145650\n" +
                "1933083289328950\n" +
                "1933017246724650\n" +
                "1933140826082650\n" +
                "1933263516351650\n" +
                "1933059626962650\n" +
                "1933299536953650\n" +
                "1933013776377650\n" +
                "1933020936093650\n" +
                "1933024036403650\n" +
                "1933013666366650\n" +
                "1933296829682950\n" +
                "1933298097809750\n" +
                "1933061037103750\n" +
                "1933279557955750\n" +
                "1933015607560750\n" +
                "1933262567256750\n" +
                "1933067717771750\n" +
                "1933025487548750\n" +
                "1933268337833750\n" +
                "1933015697569750\n" +
                "1933291280128050\n" +
                "1933290830083050\n" +
                "1933060840084050\n" +
                "1933109360936050\n" +
                "1933265490549050";
        //        System.out.println("barcodes: "+barcodes);
//        System.out.println("barcode: "+barcode); //1933024820482050
//        System.out.println("round: "+barcode.substring(2,4)); //33
//        System.out.println("group: "+barcode.substring(4,6)); //02
//        System.out.println("number: "+barcode.substring(6,10)); //4820

        List<String> barcodeList = Arrays.asList(barcodes.split("\\r?\\n"));
        for (String b : barcodeList) {
            System.out.println("========================================");
            System.out.println("barcode: " + b);
            System.out.println("round: " + b.substring(2, 4));
            System.out.println("group: " + b.substring(4, 6));
            System.out.println("number: " + b.substring(6, 10));
            System.out.println("========================================");
        }

    }
}
