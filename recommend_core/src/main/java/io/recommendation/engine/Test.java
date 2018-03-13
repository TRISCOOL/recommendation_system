package io.recommendation.engine;

public class Test {

    public static void main(String[] args){
        String str = "537/movie_favor:2/1520685335857/Put/vlen=1/seqid=0";
        System.out.println(str.substring(0,str.indexOf("/")));
        System.out.println(str.substring(str.indexOf(":")+1,str.indexOf(":")+2));
    }
}
