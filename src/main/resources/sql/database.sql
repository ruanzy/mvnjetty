count
===
select count(1) from bi_database where 1=1
@if(isNotEmpty(create_user)){
and create_user = #{create_user}
@}

list
===
select * from bi_database where 1=1
@if(isNotEmpty(create_user)){
	and create_user = #{create_user}
@}
@if(isNotEmpty(id)){
	and id = #{id}
@}
order by id DESC

