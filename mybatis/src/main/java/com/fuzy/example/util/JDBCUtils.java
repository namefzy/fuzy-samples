package com.fuzy.example.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fuzy
 * @version 1.0
 * @Description 原生的数据库链接工具类
  
 * @date 2021/5/13 11:49
 */
public class JDBCUtils {

    private static String url;

    private static String user;

    private static String password;

    private static Connection connection;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        url = resourceBundle.getString("url");
        user = resourceBundle.getString("user");
        password = resourceBundle.getString("password");
    }

    public static Connection getConnection(){
        if(connection==null){
            try {
                connection = DriverManager.getConnection(url,user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    public static void close(Connection connection){
        close(connection,null);
    }

    public static void close(Connection connection,Statement statement){
        close(connection,statement,null);
    }

    public static void close(Connection connection,Statement statement,ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static <T> List<T> query(String sql,Class clazz,Object... params)throws Exception{
        connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        if(ps!=null&&params.length>0){
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1,params[i]);
            }
        }
        ResultSet resultSet = ps.executeQuery();
        //获取对应表的元素
        ResultSetMetaData metaData = ps.getMetaData();
        List<T> list = new ArrayList<>();
        while (resultSet.next()){
            Object o = clazz.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                //获取字段值
                String value = resultSet.getString(columnName);
                //去_转换成驼峰反射给字段
                setFieldValueForColumn(o,columnName,value);
            }
            list.add((T) o);
        }
        return list;
    }

    private static void setFieldValueForColumn(Object o, String columnName, String value) {
        //如果是_分割的需要转换成驼峰格式
        if(columnName.contains("_")){
            StringBuilder sb = new StringBuilder();
            String[] split = columnName.split("_");
            sb.append(split[0]);
            for (int i = 1; i <split.length ; i++) {
                sb.append(toUpperFirstCharacter(split[i]));
            }
            columnName = sb.toString();
        }
        try{
            Class clazz = o.getClass();
            Field field = clazz.getDeclaredField(columnName);
            field.setAccessible(true);
            if(field.getType()==int.class){
                field.set(o,Integer.valueOf(value));
            }else{
                field.set(o,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String toUpperFirstCharacter(String s) {
        // 利用ascii编码的前移，效率要高于截取字符串进行转换的操作
        char[] cs = s.toCharArray();
        if (Character.isLowerCase(cs[0])) {
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return s;
    }

    public static void main(String[] args) throws Exception {

        try{
            String sql = "select * from user";
            List<UserDemo> userDemos = query(sql, UserDemo.class);
            for (UserDemo userDemo1 : userDemos) {
                System.out.println(userDemo1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
