-- https://zhuanlan.zhihu.com/p/72223558
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































