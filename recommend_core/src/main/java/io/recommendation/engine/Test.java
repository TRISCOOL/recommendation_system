package io.recommendation.engine;


public class Test {

    public static void main(String[] args){

        String str = "[[2,1.9984173],[1,1.5066733],[1,2.22222]]";
        String s1 = str.substring(1,str.lastIndexOf("]"));
        String[] strings = s1.split("],");
        for (String s : strings){
            String numbers = s.replace("[","").replace("]","");
            String[] s2 =  numbers.split(",");
            System.out.println(s2[0]);
        }


/*        String str = "537/movie_favor:2/1520685335857/Put/vlen=1/seqid=0";
        System.out.println(str.substring(0,str.indexOf("/")));
        System.out.println(str.substring(str.indexOf(":")+1,str.indexOf(":")+2));*/
    }
}
