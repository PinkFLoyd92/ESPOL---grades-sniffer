package com.fenonimous.polstalk.utils;

import java.util.Comparator;

/**
 * Created by sebas on 4/3/16.
 */
public class IntegerComparator implements  Comparator<Integer>{

    @Override
    public int compare(Integer left, Integer right) {
        if(left>right){
        return 1;
        }else if(right>left){
            return -1;
        }
        return 0;
    }
}
