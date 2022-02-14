### UML画

#### PlantUml

ImageInfo <… ImageLoader :依赖关系(在某个方法里通过传参的方式用到了某个类)

WindowManager <-- Window:关联关系(类里面作为属性引用了另一个类)

Company o-- Department:聚合关系（整体和部分）

Bird *-- Wing:组合关系（相比聚合更加紧密的关系，生死与共，不能单独存在）

Factory <|-- PhoneFactory:泛化关系

IColorDraw <|… RedColorDraw:实现关系



#### UMl类图的几种关系

在UML类图中，常见的有以下6种关系：泛化(Generalization)、实现(Realization)、关联(Association)、聚合(Aggregation)、组合(Composition)、依赖(Dependency)。

1. 泛化(Generalization)
   泛化关系：是一种继承关系，表示一般与特殊的关系，它指定了子类如何特化父类的所有特征和行为。例如：老虎是动物的一种，既有老虎的特性也有动物的共性。

   箭头指向：带三角形的实现，箭头指向父类

   <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317110123175.png" alt="image-20210317110123175" style="zoom:50%;" />

2. 实现(Realization)
   实现关系:是一种类与接口的关系，表示类是接口所有特征和行为的实现

   箭头指向：带三角箭头的虚线，箭头指向接口

   <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317110147754.png" alt="image-20210317110147754" style="zoom:50%;" />

   3. 关联(Association)
      关联关系：是一种拥有的关系，它使得一个类知道另一个类的属性和方法；如：老师与学生，丈夫与妻子关联可以是双向的，也可以是单向的。双向的关联可以有两个箭头或者没有箭头，单向的关联有一个箭头。

      代码体现：成员变量

      箭头指向：带普通箭头的实心线，指向被拥有者。

      <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317110300726.png" alt="image-20210317110300726" style="zoom:50%;" />

      4. 聚合(Aggregation)
         聚合关系：是整体与部分的关系，且部分可以离开整体而独立存在。如车和轮胎式整体和部分的关系，轮胎离开车仍然可以存在。
         聚合关系是关联关系的一种，是强的关联关系；关联和聚合在语法上无法区分，必须考察具体的逻辑关系。

         代码体现：成员变量

         箭头指向：带空心的菱形的实心线，菱形指向整体

         <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317110404998.png" alt="image-20210317110404998" style="zoom:50%;" />

         5. 组合(Composition)
            组合关系：是整体与部分的关系，但部分不能离开整体而单独存在。比如公司和部分是整体和部分的关系，没有公司就不存在部分。
            组合关系是关联关系的一种，是比聚合关系还要强的关系，它要求普通的聚合关系中代表整体的对象负责代表部分的对象的生命周期。

            代码体现：成员变量。

            箭头指向：带实心菱形的实线，菱形指向整体。

            <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317111247542.png" alt="image-20210317111247542" style="zoom:50%;" />

            6. 依赖(Dependency)
               依赖关系：是一种使用的关系，即一个类的实线需要另一个类的协助，所有要尽量不使用双向的互相依赖。

               代码体现：局部变量、方法的参数或者对静态方法的调用。

               箭头指向：带箭头的虚线，指向被使用者。

               <img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210317111332655.png" alt="image-20210317111332655" style="zoom:50%;" />

               > 各种关系的强弱顺序：
               >
               > 泛化 = 实现 > 组合 > 聚合 > 关联 > 依赖

               ![image-20210317111436079](/Users/eleme/Library/Application Support/typora-user-images/image-20210317111436079.png)

