1、输出的内容为：false

```java
public class SystemUtil{
    public static boolean isAdmin(String userId){
        return userId.toLowerCase()=="admin";
    }
    public static void main(String[] args){
        System.out.println(isAdmin("Admin"));
    }
}
```

- `toLowerCase()`的源码是`new String`，因此==比较的是堆中对象的地址，而不是常量池中的String

2、下列选项中是正确的方法声明的是：ABCD

```java
protected abstract void f1();
public final void f1() {}
static final void fq(){}
private void f1() {}
```

- A：抽象方法只可以被public 和 protected修饰；
- B：final可以修饰类、方法、变量，分别表示：该类不可继承、该方法不能重写、该变量是常量
- C：static final 可以表达在一起来修饰方法，表示是该方法是静态的不可重写的方法
- D：private 修饰方法（这太常见的）表示私有方法，本类可以访问，外界不能访问

3、以下代码执行后输出结果为：return value of getValue(): 1

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("return value of getValue(): " +
        getValue());
    }
     public static int getValue() {
         try {
             return 0;
         } finally {
             return 1;
         }
     }
 }
```

- 当Java程序执行try块、catch块时遇到了return或throw语句，这两个语句都会导致该方法立即结束，但是系统执行这两个语句并不会结束该方法，而是去寻找该异常处理流程中是否包含finally块，如果没有finally块，程序立即执行return或throw语句，方法终止；如果有finally块，系统立即开始执行finally块。只有当finally块执行完成后，系统才会再次跳回来执行try块、catch块里的return或throw语句；如果finally块里也使用了return或throw等语句，finally块会终止方法，系统将不会跳回去执行try块、catch块里的任何代码

3、java用（）机制实现了线程之间的同步执行：监视器

- 首先jvm中没有进程的概念 ，但是jvm中的线程映射为操作系统中的进程，对应关系为1：1。那这道题的问的就是jvm中线程如何异步执行 。  在jvm中 是使用监视器锁来实现不同线程的异步执行，  在语法的表现就是synchronized  。

4、将此对象序列化为文件，并在另外一个JVM中读取文件，进行反序列化，请问此时读出的Data0bject对象中的word和i的值分别为："123", 0

```java
//有以下一个对象：
public class DataObject implements Serializable{
    private static int i=0;
    private String word=" ";
    public void setWord(String word){
        this.word=word;
    }
    public void setI(int i){
        Data0bject.i=i;
     }
}
//创建一个如下方式的DataObject:
DataObject object=new Data0bject ( );
object.setWord("123");
object.setI(2);
```

- 序列化保存的是对象的状态，静态变量属于类的状态，因此，序列化并不保存静态变量。所以i是没有改变的
- 序列化的是对象，不是类，类变量不会被序列化