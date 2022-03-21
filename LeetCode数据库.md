##### Easy

###### 175、组合两个表

左连接，不管左表有无数据，都展示搜索结果

````sql
SELECT a.FirstName, a.LastName, b.City, b.State
FROM Person a
LEFT JOIN Address b
ON a.PersonId = b.PersonId
````



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



###### 627、变更性别

**CASE WHEN函数、CASE WHEN表达式、IF函数、按条件更新表**

​	1、CASE WHEN函数

````sql
UPDATE Salary
SET sex = CASE sex
			WHEN 'm'
			THEN 'f'
			ELSE 'm'
			END 
--CASE表示函数开始
--sex WHEN 'm' THEN 'f'表示若sex = 'm'，则跳出该函数且返回'f'
--ELSE表示上面所有条件都不成立时，返回'm'
--END表示函数结束
--可以多个WHEN THEN 并列，但当满足其中一个WHEN THEN时，立即跳出函数并返回值
````

​	2、CASE WHEN表达式

````sql
UPDATE Salary
SET sex = CASE 
			WHEN sex = 'f'
			THEN 'm'
			WHEN sex = 'm'
			THEN 'f'
			END
--基本与上面的函数相同，但是WHEN后可以写表达式
--也有ELSE，为了演示没写
````

​	3、IF函数

````sql
UPDATE Salary
SET sex = 
IF(sex = 'm', 'f', 'm')
--IF(expr,v1,v2),如果表达式 expr 成立，返回结果 v1；否则，返回结果v2
````

###### 1179、重新格式化部门表

**分组理解、CASE WHEN函数**

​	分组后跟的字段要么是检索列，要么是聚合函数进行汇总列（因为聚合函数之后只会返回一个值，附加到分组中，若返回多个则没法分组）

````sql
SELECT id,
	   sum(CASE month WHEN 'Jan' THEN revenue END) AS Jan_Revenue,
	   sum(CASE month WHEN 'Feb' THEN revenue END) AS Feb_Revenue,
	   sum(CASE month WHEN 'Mar' THEN revenue END) AS Mar_Revenue,
	   sum(CASE month WHEN 'Apr' THEN revenue END) AS Apr_Revenue,
	   sum(CASE month WHEN 'May' THEN revenue END) AS May_Revenue,
	   sum(CASE month WHEN 'Jun' THEN revenue END) AS Jun_Revenue,
	   sum(CASE month WHEN 'Jul' THEN revenue END) AS Jul_Revenue,
	   sum(CASE month WHEN 'Aug' THEN revenue END) AS Aug_Revenue,
	   sum(CASE month WHEN 'Sep' THEN revenue END) AS Sep_Revenue,
	   sum(CASE month WHEN 'Oct' THEN revenue END) AS Oct_Revenue,
	   sum(CASE month WHEN 'Nov' THEN revenue END) AS Nov_Revenue,
	   sum(CASE month WHEN 'Dec' THEN revenue END) AS Dec_Revenue
FROM Department
GROUP BY id 
````



##### Medium

###### 176、第二高的薪水

**第N大的数字**、**LIMIT OFFSET函数**、**IFNULL函数**、**分页思想**、**窗口函数RANK**

​	1、根据题目要求大小先排序，排序后利用LIMIT 进行分页

````sql
SELECT ifnull(
			(SELECT DISTINCT salary
			FROM Employee
			ORDER BY Salary DESC
			LIMIT 1,1), NULL) AS SecondHighestSalary
--IFNULL函数，IFNULL(字段名,exp1) 若查出的该字段不为null，则返回该字段值，否则返回exp1
--LIMIT函数，LIMIT N，取查出来的数据前N行。
--OFFSET函数，LIMIT N OFFSET M，从M行起，取N行，若不足N行则取出剩余的，也可以简写为LIMIT M,N
--注意：OFFSET是从第0行算起的，比如查出三条数据，那么OFFSET最大值为4，第一条数据下标为0
````

​	2、过滤掉最大值（仅限于求第二高或第二低情况）

````sql
SELECT max(salary) AS SecondHighestSalary 
FROM Employee
WHERE salary < (SELECT max(salary)
				FROM Employee)
````

​	3、利用窗口函数进行排名

````sql
SELECT (SELECT salary
		FROM (SELECT DISTINCT(salary),
            RANK() OVER(
			   		 	   ORDER BY salary DESC) AS rankdata
            FROM Employee) AS temp
		WHERE rankdata = 2) AS SecondHighestSalary
--最内层SELECT，选择出salary和排名列
--第二层SELECT,选择出排名第二的salary
--最外层SELECT，如果不存在排名第二的，则返回NULL
--注意：别名为temp表是为了解决MySQL每个查询表都要有别名的错误

RANK() OVER(
		PARTITION BY 字段名         --分组，但不会像GROUP BY一样进行聚合
		ORDER BY 字段名) AS 别名     --排序，正常ORDER BY使用规则
RANK() OVER函数一般用在SELECT查询子句中

专用窗口函数：rank()，dense_rank()，row_number()
窗口函数也叫OLAP函数（Online Anallytical Processing,联机分析处理），可以对数据进行实时分析处理
````

###### 177、第N高的薪水

**存储过程**、**自定义函数**、**LIMIT分页**

````sql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
set N = N-1; --因为LIMIT是从第0行开始计算，而且LIMIT内不可以写表达式，因此在计算前就要将N-1的关系写出
  RETURN (
      # Write your MySQL query statement below.
    SELECT ifnull(
        (SELECT DISTINCT salary
        FROM Employee
        ORDER BY Salary DESC
        LIMIT N,1), NULL) AS SecondHighestSalary
);
END
````

###### 178、分数排名

**窗口函数DENSE_RANK() OVER()**

​	解题思路只需理解改窗口函数即可

````sql
# Write your MySQL query statement below
SELECT score,
DENSE_RANK() OVER(
                ORDER BY score DESC) AS 'rank'
FROM Scores

注意:不同的窗口函数处理并列情况不同
--RANK函数中，如果有并列的情况，会占用下一个数字。如：1,1,3,4,5,5,7
--DENSE_RANK函数中，如果有并列的情况，不会占用下一个数字。如：1,1,2,3,4,5,5,6
--ROW_NUMBER函数中，会忽略并列的情况。如:1,2,3,4,5,6,7
````

###### 180、连续出现的数字

**窗口函数ROW_NUMBER() over()**

```sql
SELECT DISTINCT Num AS ConsecutiveNums
FROM (SELECT Num,Id,
	  ROW_NUMBER() over(PARTITION BY Num ORDER BY Id) AS flag, --根据可能重复的数字进行分组，并根据主键排序
      ROW_NUMBER() over(ORDER BY Id) as id2 --防止主键顺序不连续，造一个连续的列（如：1,2,4,7造成1,2,3,4）
	  FROM Logs
	  ORDER BY Id) AS a
GROUP BY (a.Id2-a.flag), Num
HAVING count(*) >= 3 -- 3是指连续几次

--a.Id2-a.flag：造的主键列Id2是连续的，如果某个数字一直连续出现，那么ROW_NUMBER的排序也是连续的，那么a.Id2-a.flag是一直不变的，以此来判定是否连续
--GROUP BY 连续数字 是防止特殊情况
--HAVING count（）进行统计
```

###### 184、部门工资最高的员工

**窗口函数RANK() OVER()组内排序**

```sql
SELECT Department, Employee, Salary
FROM (SELECT b.name AS Department,
		  	 a.name AS Employee,
		  	 a.salary AS Salary,
  	 RANK() OVER(PARTITION BY a.departmentId --按照部门进行排序
  	 			 ORDER BY a.salary DESC) AS num
	 FROM Employee a
	 INNER JOIN Department b
	 ON a.departmentId = b.id) AS t1
WHERE num = 1 --组内第一，也可以变成某个组内前N
```

