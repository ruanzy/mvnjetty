count
===
select count(1) from users where 1=1
@if(isNotEmpty(name)){
and name = #{name}
@}
and isadmin = 0

list
===
select * from users where 1=1
@if(isNotEmpty(name)){
and name = #{name}
@}
and isadmin = 0
order by regtime DESC