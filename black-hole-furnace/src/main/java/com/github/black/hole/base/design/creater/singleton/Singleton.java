package com.github.black.hole.base.design.creater.singleton;

/**
 * 单例模式实现方式有好多种，但大部分都会有多线程环境下的问题；
 *
 * <p>使用内部类可以避免这个问题，因为在多线程环境下，jvm对一个类的初始化会做限制，
 *
 * <p>同一时间只会允许一个线程去初始化一个类，这样就从虚拟机层面避免了大部分单例实现的问题，可以尝试下
 *
 * @author hairen.long
 * @date 2020/6/18
 */
public class Singleton {

    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {}

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** 饿汉模式 */
    public static class Singleton1 {
        private static Singleton1 instance = new Singleton1();

        private Singleton1() {}

        public static Singleton1 getInstance() {
            return instance;
        }
    }

    /** 懒汉模式（线程不安全） */
    public static class Singleton2 {
        private static Singleton2 instance;

        private Singleton2() {}

        public static Singleton2 getInstance() {
            if (instance == null) {
                instance = new Singleton2();
            }
            return instance;
        }
    }

    /** 懒汉模式（线程安全） */
    public static class Singleton3 {
        private static Singleton3 instance;

        private Singleton3() {}

        public static synchronized Singleton3 getInstance() {
            if (instance == null) {
                instance = new Singleton3();
            }
            return instance;
        }
    }

    /** 双重检查模式 （DCL） */
    public static class Singleton4 {
        private static volatile Singleton4 instance;

        private Singleton4() {}

        public static Singleton4 getInstance() {
            if (instance == null) {
                synchronized (Singleton4.class) {
                    if (instance == null) {
                        instance = new Singleton4();
                    }
                }
            }
            return instance;
        }
    }
}
