count
===
select count(1) from bi_database_source where 1=1
@if(isNotEmpty(create_user)){
and create_user = #{create_user}
@}

list
===
select bds.id as id,bd.name as database_name,bds.create_user as create_user, bds.create_time as create_time,bds.source_sql as source_sql,bds.name as name from bi_database_source bds left join bi_database bd on  bds.database_id = bd.id where 1=1
@if(isNotEmpty(name)){
	and bds.name = #{name}
@}
@if(isNotEmpty(create_user)){
	and bd.create_user = #{create_user}
@}
order by bds.id DESC

