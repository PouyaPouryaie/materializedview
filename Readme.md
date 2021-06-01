# materializedView

1) in order to generate data for test, go to the application.properties and add `app.generator.enabled=true`
after that `app.generator.enabled=false`
2) create tables and MATERIALIZED view
3) populated materialized view
4) create procedure for update view
5) time of update config in materializedViewRefresher.class 



drop table if exists

```sql
drop table if EXISTS product CASCADE;
drop table if EXISTS users CASCADE;
drop table if EXISTS purchase_order CASCADE;
```


create data base:

```sql
CREATE TABLE users(
    id serial PRIMARY KEY,
    firstname VARCHAR (50),
    lastname VARCHAR (50),
    state VARCHAR(10)
 );
 
 CREATE TABLE product(
    id serial PRIMARY KEY,
    description VARCHAR (500),
    price numeric (10,2) NOT NULL
 );
 
 CREATE TABLE purchase_order(
     id serial PRIMARY KEY,
     user_id integer references users (id),
     product_id integer references product (id)
 );
```

create view :
```sql
create view purchase_order_summary
as
select u.state,
       sum(p.price) as total_sale
from users u,
     product p,
     purchase_order po
where u.id = po.user_id
  and p.id = po.product_id
group by u.state
order by u.state
```

create materializedView

```sql
CREATE MATERIALIZED VIEW purchase_order_summary
AS
select 
    u.state,
    sum(p.price) as total_sale
from 
    users u,
    product p,
    purchase_order po
where 
    u.id = po.user_id
    and p.id = po.product_id
group by u.state
order by u.state
WITH NO DATA;
CREATE UNIQUE INDEX state_category ON purchase_order_summary (state);

-- first population:
REFRESH MATERIALIZED VIEW purchase_order_summary;
```

create procedure:

```sql
-- create procedure
CREATE OR REPLACE PROCEDURE refresh_mat_view()
LANGUAGE plpgsql    
AS $$
BEGIN
  REFRESH MATERIALIZED VIEW CONCURRENTLY purchase_order_summary;
END;
$$;
```
