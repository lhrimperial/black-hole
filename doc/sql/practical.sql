-- https://zhuanlan.zhihu.com/p/72223558
-- https://ya2.top/articles/mysql%E4%B8%AD%E7%9A%84%E7%94%A8%E6%88%B7%E5%8F%98%E9%87%8F/
-- 写的顺序：select … from… where…. group by… having… order by..
-- 执行顺序：from… where…group by… having…. select … order by…

/*
sql 的执行顺序
FROM
WHERE
GROUP BY
HAVING
SELECT
DISTINCT
UNION
ORDER BY
*/

create table Student(sid varchar(10),sname varchar(10),sage datetime,ssex nvarchar(10));
insert into Student values('01' , '赵雷' , '1990-01-01' , '男');
insert into Student values('02' , '钱电' , '1990-12-21' , '男');
insert into Student values('03' , '孙风' , '1990-05-20' , '男');
insert into Student values('04' , '李云' , '1990-08-06' , '男');
insert into Student values('05' , '周梅' , '1991-12-01' , '女');
insert into Student values('06' , '吴兰' , '1992-03-01' , '女');
insert into Student values('07' , '郑竹' , '1989-07-01' , '女');
insert into Student values('08' , '王菊' , '1990-01-20' , '女');
create table Course(cid varchar(10),cname varchar(10),tid varchar(10));
insert into Course values('01' , '语文' , '02');
insert into Course values('02' , '数学' , '01');
insert into Course values('03' , '英语' , '03');
create table Teacher(tid varchar(10),tname varchar(10));
insert into Teacher values('01' , '张三');
insert into Teacher values('02' , '李四');
insert into Teacher values('03' , '王五');
create table SC(sid varchar(10),cid varchar(10),score decimal(18,1));
insert into SC values('01' , '01' , 80);
insert into SC values('01' , '02' , 90);
insert into SC values('01' , '03' , 99);
insert into SC values('02' , '01' , 70);
insert into SC values('02' , '02' , 60);
insert into SC values('02' , '03' , 80);
insert into SC values('03' , '01' , 80);
insert into SC values('03' , '02' , 80);
insert into SC values('03' , '03' , 80);
insert into SC values('04' , '01' , 50);
insert into SC values('04' , '02' , 30);
insert into SC values('04' , '03' , 20);
insert into SC values('05' , '01' , 76);
insert into SC values('05' , '02' , 87);
insert into SC values('06' , '01' , 31);
insert into SC values('06' , '03' , 34);
insert into SC values('07' , '02' , 89);
insert into SC values('07' , '03' , 98);

-- 查询"01"课程比"02"课程成绩高的学生的信息及课程分数
select * from Student s right join (
select class1.sid,class1.score class1Score,class2.score class2Score from SC class1 inner join SC class2 
on class1.sid = class2.sid where class1.cid = '01' and class2.cid = '02' and class1.score > class2.score
) t on s.sid = t.sid;

-- 查询存在" 01 "课程但可能不存在" 02 "课程的情况(不存在时显示为 null )
select * from(
(select * from SC where cid = '01') t1 
left join (select * from SC where cid = '02') t2
on t1.sid = t2.sid);

-- 查询同时存在01和02课程的情况
select * from SC class1 inner join SC class2 on class1.sid = class2.sid 
where class1.cid = '01' and class2.cid = '02';

-- 查询选择了02课程但没有01课程的情况
select * from SC where cid = '02' and sid not in (select sid from SC where cid = '01');

-- 查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
select s.sid,s.sname,temp.avgScore from Student s inner join
(select avg(score) avgScore,sid from SC group by sid having avgScore > 60) temp
on s.sid = temp.sid;

-- 查询所有同学的学生编号、学生姓名、选课总数、所有课程的成绩总和
select s.sid,s.sname,temp.num,temp.total from Student s 
inner join (select sid,count(score) num,sum(score) total from SC group by sid) temp 
on temp.sid = s.sid;

-- 查询学过「张三」老师授课的同学的信息
select s.* from Teacher t
left join Course c on t.tid = c.tid
left join SC sc on c.cid = sc.cid
left join Student s on sc.sid = s.sid
where tname = '张三';

-- 查询没有学全所有课程的同学的信息
select s.* from Student s right join 
(select sid,count(cid) from SC group by sid having count(cid) < (select count(distinct cid) from Course)) t
on s.sid = t.sid;

-- 查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
select * from Student where sid in (select distinct sid from SC where cid in
(select cid from SC where sid = '01') and sid != '01');

-- 查询和" 01 "号的同学学习的课程完全相同的其他同学的信息
select * from Student where sid in(
select distinct sid from SC where sid != '01' and cid in(select cid from SC where sid = '01')
group by sid having count(cid) = (select count(cid) from SC where sid = '01'));

-- 查询没学过"张三"老师讲授的任一门课程的学生姓名
select * from Student where sid not in(
select sc.sid from SC sc 
left join Course c on sc.cid = c.cid
left join Teacher t on c.tid = t.tid
where t.tname = '张三');

-- 查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩
select s.*,t.vscore from Student s right join 
(select sc.sid,avg(sc.score) vscore from (select * from SC where score < 60) sc
group by sc.sid having count(sc.cid) >= 2) t on s.sid = t.sid;

-- 检索" 01 "课程分数小于 60，按分数降序排列的学生信息
select s.* from Student s right join 
(select * from SC where cid = '01' and score < 60) t on s.sid = t.sid
 order by t.score desc;

-- 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
select sc.*,s.vscore from SC sc left join 
(select sid,avg(score) vscore from SC group by sid) s
on  sc.sid = s.sid order by s.vscore desc;

-- 查询各科成绩最高分、最低分和平均分 课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
select cid 课程ID,
	count(sid) 课程人数,
    max(score) 最高分,
    min(score) 最低分,
    avg(score) 平均分,
    SUM(及格) / COUNT(sid) AS 及格率,
	SUM(中等) / COUNT(sid) AS 中等率,
	SUM(优良) / COUNT(sid) AS 优良率,
	SUM(优秀) / COUNT(sid) AS 优秀率 
    from
(select *,
	case when score >= 60 then 1 else 0 end as 及格,
    case when score >= 70 then 1 else 0 end as 中等,
    case when score >= 80 then 1 else 0 end as 优良,
    case when score >= 90 then 1 else 0 end as 优秀
from SC ) t group by cid order by count(sid) desc,cid;

-- 按各科成绩进行排序，并显示排名， Score 重复时保留名次空缺
select sc.*,t.rank from SC sc left join(
select a.cid,a.sid,count(a.score) rank from SC a 
left join SC b on a.cid = b.cid and a.score < b.score
group by a.cid,a.sid) t
on sc.sid = t.sid and sc.cid = t.cid
order by sc.cid,t.rank ;

select a.cid,a.sid,a.score ascore,b.score bscore from SC a 
left join SC b on a.cid = b.cid and a.score < b.score order by a.cid,ascore desc;

-- 按各科成绩进行行排序，并显示排名， Score 重复时合并名次
select sc.*,t.rank from SC sc left join(
select a.cid,a.sid,count(b.score)+1 rank from SC a 
left join SC b on a.cid = b.cid and a.score < b.score
group by a.cid,a.sid) t
on sc.sid = t.sid and sc.cid = t.cid
order by sc.cid,t.rank ;

SELECT 
  a.*,
  @rank := @rank + 1 AS rank 
FROM
  (SELECT 
    sid,
    SUM(score) 
  FROM
    SC 
  GROUP BY sid 
  ORDER BY SUM(score) DESC) a,
  (SELECT 
    @rank := 0) b ;



















