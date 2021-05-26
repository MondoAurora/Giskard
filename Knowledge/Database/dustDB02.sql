drop view if exists dust_unit_entities, dust_unit_state, dust_unit_dates, dust_unit_res, dust_unit_bin;

CREATE VIEW dust_unit_entities AS
select e.*
from dust_entity e
where e.LastValid is null;

CREATE VIEW dust_unit_state AS
select d.*, e.Unit
from dust_data d, dust_entity e
where d.Entity = e.EntityId and d.LastValid is null;

CREATE VIEW dust_unit_dates AS
select ev.*, e.Unit
from dust_event ev, dust_entity e
where ev.Entity = e.EntityId and ev.LastValid is null;

CREATE VIEW dust_unit_res AS
select t.*, e.Unit
from dust_text t, dust_entity e
where t.Entity = e.EntityId and t.LastValid is null;

CREATE VIEW dust_unit_bin AS
select s.*, e.Unit
from dust_stream s, dust_entity e
where s.Entity = e.EntityId and s.LastValid is null;
