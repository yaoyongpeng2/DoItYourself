package com.example.restService;

   
// public class Greeting {
//     private long id;
//     private String content;

//      public record Greeting(long id, String content){}

// }

/*
 *  record是Java 14预览，Java 16正式引入的特性，用于声明不可变的数据载体
 * record的参数是固定的，无法直接设置默认值，除非通过工厂方法或者构造方法的重载。
 * 编译器会自动为你生成以下内容：
 * ​私有 final 字段：private final long id 和 private final String content
 * ​全参构造方法：public Greeting(long id, String content)
 * ​访问器方法：public long id() 和 public String content()（注意方法名与字段名一致，没有 get 前缀）
 * ​自动实现的 equals()、hashCode()、toString()
 * 所以你的 id 和 content 数据实际上存储在编译器自动生成的私有 final 字段中，你不需要手动声明它们
 */
public record Greeting(long id, String content){}