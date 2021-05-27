-- CREATE VIEW dust_units AS
-- select e.*
-- from dust_entity e
-- where e.LastValid is null;

select * 
from dust_unit_res;

select * 
from dust_unit_state;

select * 
from dust_unit_res;

select Unit, Count(Unit) 
from dust_unit_entities
group by Unit;

select PrimaryType, Count(PrimaryType) 
from dust_unit_entities
group by PrimaryType;

delete from dust_text;
delete from dust_data;
delete from dust_entity;
ALTER TABLE dust_entity AUTO_INCREMENT = 0;
ALTER TABLE dust_text AUTO_INCREMENT = 0;
ALTER TABLE dust_data AUTO_INCREMENT = 0;

select r.* 
from dust_unit_res r
where r.Language = 1 and r.Unit = 1;

select x.* 
from dust_unit_res x
where x.Unit in (1, 2, 3);

select d.*, e.Unit
from dust_data d, dust_entity e
where d.Entity = e.EntityId and d.LastValid is null;