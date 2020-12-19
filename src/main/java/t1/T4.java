package t1;

public class T4 {

    public static void main(String[] args) {


        String g1 = "01";
        String g2 = "10";
        int i = Integer.parseInt(g1);
        int j = Integer.parseInt(g2);
        final int maxGroup = 5;

        System.out.println(">>>>>> g1 :"+ g1);
        System.out.println(">>>>>> g2 :"+ g2);
        if(maxGroup % (i - j) != 0 ){
            System.out.println(">>>>>> Yes :"+ i);

        }else {
            System.out.println(">>>>>> Nooooooo");
        }
    }
}
