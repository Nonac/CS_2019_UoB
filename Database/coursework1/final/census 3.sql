-- This file is for your solutions to the census question.
-- Lines starting with -- ! are recognised by a script that we will
-- use to automatically test your queries, so you must not change
-- these lines or create any new lines starting with this marker.
--
-- You may break your queries over several lines, include empty
-- lines and SQL comments wherever you wish.
--
-- Remember that the answer to each question goes after the -- !
-- marker for it and that the answer to each question must be
-- a single SQL query.
--
-- Upload this file to SAFE as part of your coursework.

-- !census

-- !question0

-- Sample solution to question 0.
SELECT data FROM Statistic WHERE wardId = 'E05001982' AND
occId = 2 AND gender = 0;

-- !question1

select data from Statistic where wardId='E05001975' and occid=7 and gender=1;

-- !question2

select sum(data) from Statistic where  wardId='E05000697' and occId=5;

-- !question3

select Sum(S.data) as `num_people`,O.name as `occ_class`  from Statistic S
INNER JOIN Occupation O on S.occId = O.id
where wardId='E05008884'
GROUP BY S.occId;

-- !question4

select Sum(S.data) as `working population`,S.wardId as `code`,W.name as `ward name`,
       C.name as `county-level unit name` from Statistic S
INNER JOIN Ward W on S.wardId = W.code
INNER JOIN County C on W.parent = C.code
GROUP BY S.wardId
ORDER BY Sum(S.data) ASC limit 0,1;

-- !question5

select count(*) from (select count(*) from Statistic S
GROUP BY S.wardId
HAVING SUM(S.data)>1000) as Sc;

-- !question6

drop view if exists total;
create view total as
    select sum(S.data) as `sum of ward`,R.name as region,count(*) from Statistic S
    INNER JOIN Ward W on S.wardId = W.code
    INNER JOIN County C on W.parent = C.code
    INNER JOIN Region R on C.parent = R.code
    GROUP BY S.wardId;
select region as name,convert(sum(`sum of ward`)/count(*),decimal(15,4)) as avg_size from total
group by region;

-- !question7

select CLU,occupation,(if(gender=0,'male','female')) as gender,N from (
select C.name as CLU,O.name as occupation,S.gender as gender,SUM(S.data) as N,R.name as region from Statistic S
INNER JOIN Ward W on S.wardId = W.code
INNER JOIN County C on W.parent = C.code
INNER JOIN Occupation O on S.occId = O.id
INNER JOIN Region R on C.parent = R.code
group by C.name, S.occId, S.gender
HAVING SUM(S.data)>10000
ORDER BY SUM(S.data) ASC) as SWCOR
where region='North West';

-- !question8

drop view if exists totalbyregion;
drop view if exists genderbyregion;
create view totalbyregion as
    select sum(S.data) as `sum of ward`,R.name as region from Statistic S
    INNER JOIN Ward W on S.wardId = W.code
    INNER JOIN County C on W.parent = C.code
    INNER JOIN Region R on C.parent = R.code
    GROUP BY R.name;
create view genderbyregion as
    select R.name as `region name`,SUM(SM.data) as male,SUM(SF.data) as female from Statistic SM
    INNER JOIN Ward W on SM.wardId = W.code
    INNER JOIN County C on W.parent = C.code
    INNER JOIN Region R on C.parent = R.code
    INNER JOIN Statistic SF on SF.wardId = W.code
    where SM.occId=1 and SF.occId=1 and SM.gender=0 and SF.gender=1
    group by R.name;
select `region name`,male,female,female/`sum of ward`*100 as proportion from totalbyregion,genderbyregion
where totalbyregion.region=genderbyregion.`region name`
ORDER BY proportion asc ;

-- !question9

drop view if exists total;
create view total as
    select sum(S.data) as `sum of ward`,R.name as region,count(*) from Statistic S
    INNER JOIN Ward W on S.wardId = W.code
    INNER JOIN County C on W.parent = C.code
    INNER JOIN Region R on C.parent = R.code
    GROUP BY S.wardId;
select case
    when region is not null then region else 'England' end as name ,
       convert(sum(`sum of ward`)/count(*),decimal(15,4)) as avg_size from total
group by region with rollup
union all
select 'ALL',sum(population)/count(*) from (
    select count(*),sum(S.data) as population from Statistic S GROUP BY S.wardId
    ) as Sc;

-- !end
