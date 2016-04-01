package com.example.clone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 掌握克隆的操作, 理解并能区分克隆过程中的浅拷贝(shallow copy)与深拷贝(deep copy)
 * 
 * 根据JDK Object.clone()方法以及 Cloneable接口的要求, 要实现克隆, 必须满足如下条件:
 * 1. x.clone != x
 * 2. x.clone.getClass() == x.getClass()
 * 3. x.clone.equals(x)
 * 
 * 要进行克隆的类的三部曲:
 * 1. 实现 Cloneable接口
 * 2. 重写 clone()方法, 但需要将返回值由 Object类型变为该类的类型, 可以将 protected修饰符变为 public
 * 3. 在重写的clone()方法中, 按照惯例通常是返回 super.clone()向下转型后的值, 但是在此之前需要单独处理该类中可变的(mutable, 即: 非final的)引用类型的字段的深拷贝.
 *    因为super.clone()默认执行的是所有字段的浅拷贝(相当于赋值语句, 而赋值语句赋的仅仅是对象的内存地址而非对象本身), 但是基本类型的字段和不可变的(immutable, 即: final的)
 *    引用类型字段只需浅拷贝(也就是只需执行默认的 super.clone())即可实现克隆.
 * 
 * 参考以下几篇文章:
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Cloneable.html
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone()
 * http://javarevisited.blogspot.jp/2013/09/how-clone-method-works-in-java.html
 * http://javarevisited.blogspot.jp/2015/01/java-clone-tutorial-part-2-overriding-with-mutable-field-example.html
 * http://javarevisited.blogspot.jp/2014/03/how-to-clone-collection-in-java-deep-copy-vs-shallow.html
 * http://java67.blogspot.jp/2013/05/difference-between-deep-copy-vs-shallow-cloning-java.html
 * http://javarevisited.blogspot.jp/2013/03/how-to-create-immutable-class-object-java-example-tutorial.html
 */
public class CloneDemo {

    private static Programmer original;
    private static Programmer clone;

    public static void main(String args[]) {
	// 一个30岁的程序员John
        original = new Programmer("John", 30);
        // 30岁获得了证书A
        original.addCertificate("A");
        // 学会了踢足球, 并将足球作为一项业余爱好
        original.addHobby(new Hobby("football"));
        // 办了第一张银行卡, 向卡内存入 1000.5元.
        original.addDepositCardBalance(1000.5f);
        original.setSkilledLanguages(new String[]{"C++", "PHP"});

        clone = original.clone();
        print();

        // John 改名为 Mike.
        original.setName("Mike");
        System.out.println("--------- change name to Mike ----------------------------");
        print();
        
        // 5年后, 变为35岁了
        original.setAge(35);
        System.out.println("--------- change age to 35 ------------------------------");
        print();
        
        // 获得了证书B
        original.addCertificate("B");
        System.out.println("--------- Add a new certificate B -------------------------");
        print();
        
        // 学会了打网球, 并作为除了足球以外的又一项新的爱好
        original.addHobby(new Hobby("tennis"));
        System.out.println("--------- Add a new hobby tennis --------------------------");
        print();
        
        // 新办了一张银行卡, 卡内存有 5000.0元的金额.
        original.addDepositCardBalance(5000.0f);
        System.out.println("--------- Add a new deposit card balance 5000.0f ----------------------");
        print();
        
        // 由于这几年一直从事Java开发, 所以PHP逐渐淡忘了, 但由于工作中也经常做JNI的开发, 所以依然熟练掌握着 C++.
        String[] skilledLanguages = original.getSkilledLanguages();
        for (int i = 0; i < skilledLanguages.length; i++) {
	    String language = skilledLanguages[i];
	    if ("PHP".equals(language)) {
		skilledLanguages[i] = "Java";
		original.setSkilledLanguages(skilledLanguages);
		break;
	    }
        }
        System.out.println("--------- Change skilled languages to C++ & Java ------------------------");
        print();
    }

    private static void print() {
	System.out.println("Original programmerA : " + System.lineSeparator() + original);
        System.out.println("Clone of programmerA : " + System.lineSeparator() + clone + System.getProperty("line.separator"));
    }
}

/**
 * 程序员
 */
class Programmer implements Cloneable {
    private String name; 
    private int age;
    // 所有的获奖证书
    private List<String> certifications;
    // 所有的业余爱好
    private Set<Hobby> hobbies;
    // 他的每一张银行卡内的余额
    private List<Float> depositCardsBalances;
    // 熟练掌握的编程语言.
    private String[] skilledLanguages;

    public Programmer(String name, int age) {
        this.name = name;
        this.age = age;
        this.certifications = new ArrayList<String>();
        hobbies = new HashSet<Hobby>();
        depositCardsBalances = new ArrayList<Float>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCertificate(String cert){
        certifications.add(cert);
    }

    public void addHobby(Hobby hobby) {
    	hobbies.add(hobby);
    }
    
    public void addDepositCardBalance(float newCardBalance) {
    	depositCardsBalances.add(newCardBalance);
    }
    
    public void setSkilledLanguages(String[] languages) {
    	this.skilledLanguages = languages;
    }
    
    public String[] getSkilledLanguages() {
	return skilledLanguages;
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(name).append(", ").append(age).append(", ").append(System.lineSeparator())
    	  .append("certifications: ").append(certifications.toString()).append(", ").append(System.lineSeparator())
    	  .append("hobbies: ").append(hobbies.toString()).append(", ").append(System.lineSeparator())
    	  .append("depositCardsBalances: ").append(depositCardsBalances.toString()).append(", ").append(System.lineSeparator());
    	
    	if (skilledLanguages != null) {
    	    sb.append("skilledLanguages: ");
	    for (String language : skilledLanguages) {
		sb.append(language).append(", ");
  	    }
	    sb.append(System.lineSeparator());
    	}
    	
    	return sb.toString();
    }

    @Override
    protected Programmer clone() {
        Programmer clone = null;
        try {
	    // super.clone()默认是浅拷贝.
            clone = (Programmer) super.clone();
            clone.certifications = new ArrayList(certifications); //deep copying
            clone.hobbies = new HashSet(hobbies); //deep copying
            clone.depositCardsBalances = new ArrayList<Float>(depositCardsBalances); //deep copying
            clone.skilledLanguages = new String[skilledLanguages.length];
            for (int i = 0; i < skilledLanguages.length; i++) {
            	clone.skilledLanguages[i] = skilledLanguages[i];
            }
           
        } catch(CloneNotSupportedException cns) {
	    System.out.println("Error while cloning programmer" + cns);
        }
        // 基本类型的字段, 不可变的(immutable, 即: final的)引用类型字段(如: String, Integer等基本类型的包装类)是无需处理的, 
        // 直接使用super.clone()的浅拷贝即可, 但是其他引用类型的字段必须单独处理各自的深拷贝.
        return clone;
    }
}

/**
 * 业余爱好
 */
class Hobby {
    private String name;

    public Hobby(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return name;
    }
}
