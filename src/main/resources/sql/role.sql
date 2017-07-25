rolecount
===
select count(1) from role where 1=1
@if(isNotEmpty(name)){
and name = #{name}
@}

rolelist
===
select * from role where 1=1
@if(isNotEmpty(name)){
and name = #{name}
@}

listAll
===
select * from role