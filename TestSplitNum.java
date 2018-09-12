package com.honghe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSplitNum {

    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 1; i <= 101; i++) {
            list.add(i);
        }

        List tempList = new ArrayList();
        Map map = new HashMap<>();
        int count = 0;
        int size = 0;
        int length = list.size();

        int v = 10 ;

        for (int i=0; i<list.size(); i++) {
            tempList.add(list.get(i));
//            if ((Integer)(list.get(i)) % 8 == 0) {
            if ((Integer)(list.get(i)) % v == 0) {
                count++;
//                size += 8;
                size += v;
                i = -1;
                map.put(count, tempList);
                list.removeAll(tempList);
                tempList = new ArrayList();
            }
        }

        if (size < length) {
            List list1 = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                list1.add(list.get(i));
            }
            map.put(++count, list1);
        }
        System.out.println(map);

    }

}
