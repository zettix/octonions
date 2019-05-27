package com.zettix.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class Main {

    public static void random() {
        // Linear recursive generator with happy-slappy values:
        //
        //Octonion X_n+1 = (aX_n + c) mod m
        //  so random with a = 4, c = 1, m = 9
        Map<Octonion, Integer> placemap = new HashMap<>();
        int[] four = {4,4,4,4,4,4,4,4};
        int[] one =  {1,1,1,1,1,1,1,1};
        int[] seed = {1,2,3,4,5,6,7,8};
        Set<Octonion> seen = new HashSet<>();
        Octonion bigfour = Octonion.GetFromVector(four);
        Octonion bigone = Octonion.GetFromVector(one);
        Octonion acc = Octonion.GetFromVector(seed);
        System.out.println(acc);
        placemap.put(acc, -1);
        for (int i = 0; i < 100000; i++) {
            //acc = acc.mul(bigfour).add(bigone).mod(9);
            ///acc = bigone.mul(acc).mul(bigone).add(bigone).mod(9);
            acc = acc.copy();
            acc.rot(1);
            acc = acc.mul(bigfour).add(bigone).mod(7);
            //acc = bigone.mul(acc).mod(11);
            //acc = acc.mul(acc).add(bigfour).mod(9);
            //acc = acc.mul(bigfour).add(bigone).mod(9);
            if (i % 100000 == 0) {
                System.out.println(i + ":" + acc);
            }
            if (placemap.containsKey(acc)) {
                System.out.println("I've seen this key:" + acc + " at " + placemap.get(acc));
                 break;
            }
            if (seen.contains(acc)) {
                System.out.println("I've setseen this: " + acc + " before");
                break;
            }
            placemap.put(acc, i);
            seen.add(acc);
        }
        System.out.println("Loop size:" + seen.size());
    }

    public static void main(String[] args) {
	// write your code here
        Octonion a = Octonion.GetZero();
        System.out.println("Octo: "+ a);
        a.shift(1).set(4, 100);
        System.out.println("Octo: "+ a);
        Octonion b = a.mul(a);
        System.out.println("Octo: "+ b);
        Octonion x = Octonion.GetZero();
        x.shift(1);
        Octonion y = Octonion.GetZero();
        y.shift(1).shift(0);
        for (int i = 0; i < 10; i++) {
            Octonion z = x.mul(y);
            System.out.println("X:" + x + " Y:" + y + " Z:" + z);
            y = z;
        }
        Octonion ones1 = Octonion.GetZero();
        Octonion ones2 = Octonion.GetZero();
        for (int i = 0; i < 8; i++) {
            ones1.shift(1);
            ones2.shift(1);
        }
        Octonion ohon = ones1.mul(ones2);
        System.out.println("oo:" + ones1 + " ot:" + ones2 + " ooxot:" + ohon);
        random();
    }
}
