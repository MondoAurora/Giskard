-- CREATE VIEW dust_units AS
-- select e.*
-- from dust_entity e
-- where e.LastValid is null;

select r.* 
from dust_unit_res r
where r.Language = 1 and r.Unit = 1;

select x.* 
from dust_unit_entities x
where x.Unit in (1, 2, 3);
