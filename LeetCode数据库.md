##### Easy

###### 181、超过经理收入的员工

​	内联，重新组合本表，on条件是关键。且该题目要求查询的是对应经理，而非所有经理，所以连接条件需要把员工和经理一一对应起来

````sql
SELECT a.name AS Employee
FROM Employee a
INNER JOIN Employee b
ON a.managerId = b.id
WHERE a.salary > b.salary 
````



###### 182、查找重复的电子邮箱

**查找重复数据**

​	进行分组，用count统计数量，用having进行分组数据的过滤

````sql
SELECT Email
FROM Person
GROUP BY Email
HAVING count(Email) > 1
````



###### 183、从不订购的客户

​	1、左连接，连接两张表，若CustomerId字段为null，则说明没有订购，侧面表现LEFT JOIN是左表有数据即可，右表会用NULL补全

````sql
SELECT Name AS Customers
FROM Customers a
LEFT JOIN Orders b
ON a.Id = b.CustomerId
WHERE b.CustomerId IS NULL 
````



​	2、子查询，配合WHERE NOT IN（另一表的字段）

````sql
SELECT Name AS Customers
FROM Customers
WHERE Id NOT IN (SELECT CustomerId
				FROM Orders)
````



###### 196、删除重复的电子邮箱

**删除重复数据，并保留主键最小的记录**

​	1、自连接，类似于181，重新组合本表，如果存在相同的邮箱，那么id一定是不同的，然后再比较id，即可保留id最小的记录

````sql
DELETE a
FROM Person a
INNER JOIN Person b 
ON a.email = b.email --自连接条件为email
WHERE a.id > b.id --DELETE a，删除的是a表，所以a.id > b.id的记录要删除
````



​	2、子查询，WHERE中，分组筛选出重复且利用MIN函数得到最小id的记录即可。注意MySQL中，不可以先SELECT同一表，然后再UPDATE或者DELETE这个表

````sql
DELETE FROM Person
WHERE email IN (SELECT a.email
                FROM ((SELECT email
				        FROM Person
				        GROUP BY email
				        HAVING count(email) > 1)) a)
AND id NOT IN (SELECT a.id
                FROM ((SELECT min(id) as id
				        FROM Person
				        GROUP BY email
				        HAVING count(email) > 1)) a)
````



###### 197、上升的温度

**利用DATEDIFF进行日期对比，并添加到连接表ON条件中。只有一张表而且要对比自身时，考虑自连接**



````sql
前参数减后参数
mysql> SELECT DATEDIFF('2007-12-31 23:59:59','2007-12-30');
        -> 1
mysql> SELECT DATEDIFF('2010-11-30 23:59:59','2010-12-31');
        -> -31
````

​	进行自连接，连接条件是主表的日期大于从表一天，把主表当成“今天”，从表当成“昨天”，因此选择的是主表.id，且主表的温度>从表的温度，才是今天的温度>昨天

````sql
SELECT a.id
FROM Weather a
INNER JOIN Weather b 
ON DATEDIFF(a.recordDate, b.recordDate) = 1
WHERE a.Temperature > b.Temperature
````



###### 595、大的国家

​	基础题

````sql
SELECT name, population, area
FROM World
WHERE area >= 3000000
OR population >= 25000000
````



###### 596、超过5名学生的课

​	基础题

````sql
SELECT class
FROM Courses
GROUP BY class
HAVING count(*) >= 5 
````



###### 620、有趣的电影

**MOD函数，取模函数**

​	基础题

````sql
SELECT id, movie, description, rating
FROM cinema
WHERE description != 'boring'
AND mod(id, 2) = 1 --id%2，id对2取模，若id为奇数，则返回1，否则返回0
ORDER BY rating DESC
````



