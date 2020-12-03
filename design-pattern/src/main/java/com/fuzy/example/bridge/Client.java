package com.fuzy.example.bridge;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/12/3 7:28
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        House house = new House();
        System.out.println("房地产公司是这样运行的");
        HouseCorp houseCorp = new HouseCorp(house);
        houseCorp.makeMoney();

        ShanZaiCorp shanZaiCorp = new ShanZaiCorp(new IPod());
        shanZaiCorp.makeMoney();
    }
}
