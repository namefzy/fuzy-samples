package com.fuzy.example.prototype;

import java.util.*;
import java.util.ArrayList;

/**
 * @ClassName DeepClone
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/11 7:33
 * @Version 1.0.0
 */
public class DeepClone implements Cloneable{

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepClone deepClone = null;
        try {
            deepClone = (DeepClone) super.clone();
            this.arrayList = (ArrayList<String>) this.arrayList.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return deepClone;
    }
}
