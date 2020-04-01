package com.lichee.racksecure;

import com.lichee.racksecure.util.BCrypt;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test1{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        input.nextLine();
        Map<Integer, Integer> map = new HashMap<>();
        int min = -1;
        while(input.hasNext()){
            String[] s = input.nextLine().split(" ");
            if(s.length==2){
                int t = Integer.parseInt(s[0]);
                boolean flag = false;
                for(int key : map.keySet()){
                    if(key>= t-30 && key<= t+30)
                        flag = true;
                }
                if(flag) continue;
                int c = Integer.parseInt(s[1]);
                map.put(t,c+map.getOrDefault(t, 0));
                if(min == -1)
                    min = t;
                else
                    min = Math.min(min, t);
            }
            if(s.length==1){
                if(s[0].equals("-1")){
                    if(min==-1)
                        System.out.println("skipped");
                    else{
                        System.out.println(map.remove(min));
                        min = -1;
                        for(int key : map.keySet())
                            min = Math.min(min, key);
                    }
                }else{
                    int t = Integer.parseInt(s[0]);

                    System.out.println(map.getOrDefault(t, 0));
                }
            }
        }
    }
    @Test
    public void bcrypt(){
        String password = "lzr13579.";
        long start=System.currentTimeMillis();   //获取开始时间
//        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

// gensalt's log_rounds parameter determines the complexity
// the work factor is 2**log_rounds, and the default is 10
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(11));
        System.out.println(hashed);
        String candidate = "lzr13579.";
// Check that an unencrypted password matches one that has
// previously been hashed
        if (BCrypt.checkpw(candidate, hashed))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
        long end=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(end-start)/1000.0+"s");

    }
}