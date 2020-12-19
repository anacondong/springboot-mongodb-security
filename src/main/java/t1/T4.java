package t1;

import com.lottomatching.utils.Utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class T4 {

    public static void main(String[] args) {


        String g1 = "06";
        String g2 = "09";
        DecimalFormat groupDf = new DecimalFormat("00");
        Map groupMap = Utils.getGroupMap();

        for(int i=0; i < 100; i++){
            g1 = groupDf.format((Math.random() * 10) + 1);
            g2 = groupDf.format((Math.random() * 10) + 1);
            System.out.println("================");
            System.out.println(">>>>>> g1 :"+ g1);
            System.out.println(">>>>>> g2 :"+ g2);
            if(groupMap.get(g1).equals(groupMap.get(g2))){
                System.out.println(">>>>>> yes <<<<");
            }else {
                System.out.println(">>>>>> No <<<<");
            }
            System.out.println("================");
        }


    }
}
