package com.fuzy.example.futuredemo;

/**
 * @ClassName Server
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/18 23:05
 * @Version 1.0.0
 */
public class Server {
    public FutureData<String> getString(){
        final FutureData<String> data = new FutureData<>();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 结果写回FutureData
            data.setData("world");
        }).start();
        return data;
    }
}
