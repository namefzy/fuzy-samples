package com.fuzy.example.demo;

import com.fuzy.example.domain.ReflectDomain;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author fuzy
 * @version 1.0
 * @Description 反射测试类
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/28 17:37
 */
public class ReflectDemo {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.fuzy.example.domain.ReflectDomain");
        // instantiateByConstructor(args, clazz);
        //无参构造实例化
        //instantiateByClass(clazz);
        //instantiateByField(clazz);

        instantiateByMethod(clazz);
    }

    /**
     * 通过方法实例化
     * @author fuzy
     * @email fuzy@ufen.cn
     * @date  2021/5/28 18:06
     * @param
     */
    private static void instantiateByMethod(Class<?> clazz) throws Exception{
        ReflectDomain o = (ReflectDomain)clazz.newInstance();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            int parameterCount = method.getParameterCount();
            Class<?>[] parameterTypes = method.getParameterTypes();

            method.setAccessible(true);
            if(parameterCount==0){
                method.invoke(o,null);
            }else if(parameterCount==1&&parameterTypes[0]==int.class){
                method.invoke(o,1);
            }else if(parameterCount==1&&parameterTypes[0]==String.class){
                method.invoke(o,"1234");
            }
        }
        System.out.println("用户名："+o.getName()+"---用户年龄："+o.getAge());
    }

    /**
     * 通过字段实例化
     * @param clazz
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void instantiateByField(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        ReflectDomain o = (ReflectDomain)clazz.newInstance();
        //getDeclaredFields 访问所有的变量；getFields 私有变量无法访问
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //强制访问私有变量
            field.setAccessible(true);
            if(field.getType()==int.class){
                field.set(o,1);
            }else{
                field.set(o,"傅某");
            }
        }
        System.out.println("用户名："+o.getName()+"---用户年龄："+o.getAge());
    }

    /**
     * 通过类名实例化
     * @param clazz
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void instantiateByClass(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        ReflectDomain domain = (ReflectDomain) clazz.newInstance();
        domain.say("类实例化say方法");
    }

    /**
     * 构造方法初始化对象
     * @param args
     * @param clazz
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private static void instantiateByConstructor(String[] args, Class<?> clazz) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        //获取无参构造方法
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            int parameterCount = constructor.getParameterCount();
            if(parameterCount==0){
                ReflectDomain domain = (ReflectDomain) constructor.newInstance(args);
                domain.say("无参构造实例化调用say方法");
            }else if(parameterCount==2){
                ReflectDomain domain = (ReflectDomain) constructor.newInstance("fuzy",29);
                domain.say("有参构造实例化调用say方法");
            }

        }
    }
}
