-- The comment lines starting -- ! are used by the automatic testing tool to
-- help mark your coursework. You must not modify them or add further lines
-- starting with -- !. Of course you can create comments of your own, just use
-- the normal -- to start them.

-- !elections

-- !question0
-- This is an example question and answer.

SELECT Party.name FROM Party 
JOIN Candidate ON Candidate.party = Party.id 
WHERE Candidate.name = 'Mark Bradshaw';

-- !question1

select P.name from Party P order by P.name ASC;

-- !question2

select sum(votes) from Candidate;

-- !question3

select C.name,C.votes from Candidate C
INNER JOIN Ward W on C.ward = W.id
where W.name='Bedminster';

-- !question4

select C.votes from Candidate C
INNER JOIN Ward W on C.ward = W.id
INNER JOIN Party P on C.party = P.id
where W.name='Filwood' and P.name='Liberal Democrat';

-- !question5

drop table if exists `Hengrove ward`;
create table `Hengrove ward` as
select C.name as `name`,C.party as `party`,C.votes as `votes` from Candidate C
INNER JOIN Ward W on C.ward = W.id
where W.name='Hengrove'
ORDER BY C.votes DESC ;

-- !question6

select COUNT(IF(CL.votes>=CR.votes,true,null)) as m from Candidate CL
INNER JOIN Candidate CR
INNER JOIN Ward W on CR.ward = W.id and CL.ward=W.id
INNER JOIN Party P on CR.party = P.id
where W.name='Bishopsworth' and P.name='Labour';

-- !question7

select W.name,convert(CR.votes/sum(CL.votes)*100,decimal (15,4)) from Candidate CL
INNER JOIN Candidate CR on CR.ward=CL.ward
INNER JOIN Party P on CR.party = P.id
INNER JOIN Ward W on CL.ward = W.id
where P.name='Green'
group by CL.ward;

-- !question8

drop table if exists `Greeen beat Labour`;
create table `Greeen beat Labour` as
select W.name as `ward`,convert((CL.votes-CR.votes)/W.electorate*100,decimal (15,4)) as `rel`,
       CL.votes-CR.votes as `abs` from Candidate CL
INNER JOIN Ward W on CL.ward = W.id
INNER JOIN Candidate CR on CR.ward=CL.ward
INNER JOIN Party PL on CL.party = PL.id
INNER JOIN Party PR on CR.party = PR.id
where PL.name='Green' and PR.name='Labour' and CL.votes>CR.votes
group by CL.ward;

-- !end
