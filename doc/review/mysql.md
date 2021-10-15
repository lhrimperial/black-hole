#### MySql

慢sql

https://tech.meituan.com/2014/06/30/mysql-index.html

explain

https://blog.csdn.net/wuseyukui/article/details/71512793

锁

https://vincentruan.github.io/2020/02/12/MySQL%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5%E9%83%BD%E4%BC%9A%E7%94%A8%E5%88%B0%E4%BB%80%E4%B9%88%E9%94%81/



#### MySql 排序原理

1. 常规排序
   a. 从表 t1 中获取满足 WHERE 条件的记录
   b. 对于每条记录，将记录的主键 + 排序键 (id,col2) 取出放入 sort buffer
   c. 如果 sort buffer 可以存放所有满足条件的 (id,col2) 对，则进行排序;否则 sort buffer 满后，进行排序并固化到临时文件中。(排序算法采用的是快速排序算法)
   d. 若排序中产生了临时文件，需要利用归并排序算法，保证临时文件中记录是有序的
   e. 循环执行上述过程，直到所有满足条件的记录全部参与排序
   f. 扫描排好序的 (id,col2) 对，并利用 id 去捞取 SELECT 需要返回的列 (col1,col2,col3)
   g. 将获取的结果集返回给用户。

   > 两次IO：一次是捞 (id,col2), 第二次是捞 (col1,col2,col3)
   >
   > 结果按col2排序，ID是乱序，回表时是随机IO，mysql本身做了优化将ID排序之后再回表

2. 优化排序

   将所有需要返回的字段都放入sort buffer，而不仅仅是排序字段和主键，这样可以减少回表IO，只有单排序元组小于max_length_for_sort_data时才用优化排序

3. 优先队列排序

#### 优化

1. 优先选择usering_index 的排序方式
2. 加大 max_length_for_sort_data 参数的设置
3. 去掉不必要的返回字段
4. 增大 sort_buffer_size 参数设置



#### explain

|  id  | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra |
| :--: | :---------: | :---: | :--: | :-----------: | :--: | :-----: | :--: | :--: | :---: |
|      |             |       |      |               |      |         |      |      |       |

**其中最重要的字段为：id、type、key、rows、Extra**

1. Id ：select查询的序列号，包含一组数字，表示查询中执行select子句或操作表的顺序
   - **id相同**：执行顺序由上至下
   - **id不同**：如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
   - **id相同又不同（两种情况同时存在）**：id如果相同，可以认为是一组，从上往下顺序执行；在所有组中，id值越大，优先级越高，越先执行

2. select_type ：查询的类型，主要是用于区分普通查询、联合查询、子查询等复杂的查询
   - SIMPLE：简单的select查询，查询中不包含子查询或者union
   - PRIMARY：查询中包含任何复杂的子部分，最外层查询则被标记为primary
   - SUBQUERY：在select 或 where列表中包含了子查询
   - DERIVED：在from列表中包含的子查询被标记为derived（衍生），mysql或递归执行这些子查询，把结果放在零时表里
   - UNION：若第二个select出现在union之后，则被标记为union；若union包含在from子句的子查询中，外层select将被标记为derived
   - UNION RESULT：从union表获取结果的select

3. type：访问类型，sql查询优化中一个很重要的指标，结果值从好到坏依次是：

   ```sql
   system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL
   ```

   一般来说，好的sql查询至少达到range级别，最好能达到ref

   1、system：表只有一行记录（等于系统表），这是const类型的特例，平时不会出现，可以忽略不计

   2、const：表示通过索引一次就找到了，const用于比较primary key 或者 unique索引。因为只需匹配一行数据，所有很快。如果将主键置于where列表中，mysql就能将该查询转换为一个const

   3、**eq_ref**：唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键 或 唯一索引扫描。

   4、**ref**：非唯一性索引扫描，返回匹配某个单独值的所有行。本质是也是一种索引访问，它返回所有匹配某个单独值的行，然而他可能会找到多个符合条件的行，所以它应该属于查找和扫描的混合体

   5、**range**：只检索给定范围的行，使用一个索引来选择行。key列显示使用了那个索引。一般就是在where语句中出现了bettween、<、>、in等的查询。这种索引列上的范围扫描比全索引扫描要好。只需要开始于某个点，结束于另一个点，不用扫描全部索引

   6、**index**：Full Index Scan，index与ALL区别为index类型只遍历索引树。这通常为ALL块，应为索引文件通常比数据文件小。（Index与ALL虽然都是读全表，但index是从索引中读取，而ALL是从硬盘读取）

   7、**ALL**：Full Table Scan，遍历全表以找到匹配的行