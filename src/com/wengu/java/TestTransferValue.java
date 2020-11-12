package com.wengu.java;

/**
 * @author LoserJohn
 * @create 2020-11-12 11:34
 */
public class TestTransferValue {
    public void changValueStr(String str ){
        str = new String ("xxx");
    }
    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        String str = new String("abc");
        test.changValueStr(str);
        System.out.println("str为----" + str);
    }
}
