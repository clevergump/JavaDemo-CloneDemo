# JavaDemo-CloneDemo
这是一个关于Java Cloneable接口以及 `clone()` 方法的demo工程, 

## 一个对象x和他克隆出来的对象x.clone(), 二者要满足如下条件:
1. x.clone != x
2. x.clone.getClass() == x.getClass()
3. x.clone.equals(x)

## `super.clone()` 所产生的效果:
1. 创建一个与当前对象类型相同的新对象(这是两个不同的对象).
2. 如果原对象中包含有字段, 那么在新对象中也创建完全相同的引用, 但是这些引用于原对象内对应的引用指向的是同一个对象. 所以, `super.clone()`生成的是当前对象的浅拷贝.

## 浅拷贝与深拷贝的概念:
- 浅拷贝(shallow copy): 拷贝的仅仅是引用, 即: 仅仅是内存地址, 而不是对象本身, 所以没有创建新对象.
- 深拷贝(deep copy): 拷贝的是对象本身, 也就是创建了一个新对象.

## 哪些字段需要浅拷贝? 哪些字段需要深拷贝?
- 浅拷贝: 对于基本数据类型的字段和final的引用数据类型的字段(如: String, 以及所有基本类型的包装类等), 由于浅拷贝和深拷贝的效果是相同的, 所以这些类型的字段可以直接使用 `super.clone()` 默认生成的浅拷贝.
- 深拷贝: 对于非final的引用类型的字段, 必须使用深拷贝, 深拷贝需要我们在重写的 `clone()` 方法中手动处理.

## 一个类要成功实现克隆的三个步骤:
1. 实现 `Cloneable`接口
2. 重写 `clone()` 方法, 但需要将返回值由 Object类型变为该类的类型, 另外也可以将 protected 修饰符变为 public.
3. 在重写的 `clone()` 方法中, 按照惯例通常是返回 `super.clone()` 向下转型后的值, 但是在执行返回语句之前, 需要单独处理该类中可变的 (mutable, 即: 非final的) 引用类型的字段的深拷贝.

## 参考资料:
- [JDK-Cloneable](https://docs.oracle.com/javase/7/docs/api/java/lang/Cloneable.html)
- [JDK-Object#clone()](https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone())
- [how-clone-method-works-in-java](http://javarevisited.blogspot.jp/2013/09/how-clone-method-works-in-java.html)
- [java-clone-tutorial-part-2-overriding-with-mutable-field-example](http://javarevisited.blogspot.jp/2015/01/java-clone-tutorial-part-2-overriding-with-mutable-field-example.html)
- [how-to-clone-collection-in-java-deep-copy-vs-shallow](http://javarevisited.blogspot.jp/2014/03/how-to-clone-collection-in-java-deep-copy-vs-shallow.html)
- [difference-between-deep-copy-vs-shallow-cloning-java](http://java67.blogspot.jp/2013/05/difference-between-deep-copy-vs-shallow-cloning-java.html)
- [how-to-create-immutable-class-object-java-example-tutorial](http://javarevisited.blogspot.jp/2013/03/how-to-create-immutable-class-object-java-example-tutorial.html)