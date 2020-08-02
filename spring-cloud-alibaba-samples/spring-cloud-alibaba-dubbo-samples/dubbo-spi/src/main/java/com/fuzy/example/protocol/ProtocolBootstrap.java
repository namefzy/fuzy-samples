package com.fuzy.example.protocol;

import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

/**
 * @ClassName ProtocolBootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/7/19 21:22
 * @Version 1.0.0
 */
public class ProtocolBootstrap {
    public static void main(String[] args) {
        System.out.println(ExtensionFactory.class.getName());
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol myProtocol = extensionLoader.getExtension("myProtocol");
        myProtocol.destroy();
    }
}
