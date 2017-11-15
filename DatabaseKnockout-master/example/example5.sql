create view view1 as
select cust, prod, avg (quant) as avg_1
from sales
group by cust, prod
;

create view view2 as
select a.cust, a.prod, avg (b.quant) as avg_2
from view1 a, sales b
where a.cust <> b.cust and a.prod=b.prod
group by a.cust, a.prod
;

select a.cust, a.prod, a.avg_1 as "1_avg_quant", b.avg_2 as "2_avg_quant"
from view1 a, view2 b
where a.cust=b.cust and a.prod=b.prod
;
