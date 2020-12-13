package t1;

import java.text.DecimalFormat;

public class T2 {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i < 100; i++) {
            System.out.println("set number:" + df.format(i));
        }
    }
}

