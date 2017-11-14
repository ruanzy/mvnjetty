rolecount
===
select count(1) from role where 1=1
#if(name){
and name = #(name)
#end

rolelist
===
select * from role where 1=1
#if(name){
and name = #(name)
#end

listAll
===
select * from role