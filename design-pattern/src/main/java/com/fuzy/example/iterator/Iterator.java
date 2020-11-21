package com.fuzy.example.iterator;

/**
 * @ClassName Iterator
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 10:39
 * @Version 1.0.0
 */
public interface Iterator<E> {
    E next();

    boolean haxNext();

    boolean remove();
}
