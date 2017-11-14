count
===
select count(1) from logs where 1=1
#if(operator){
and operator = #(operator)
#end
#if(begintime){
and time >= #(begintime)
#end
#if(endtime){
and time <= #(endtime)
#end
#if(memo){
and memo like #(memo)
#end

list
===
select * from logs where 1=1
#if(operator){
and operator = #(operator)
#end
#if(begintime){
and time >= #(begintime)
#end
#if(endtime){
and time <= #(endtime)
#end
#if(memo){
and memo like #(memo)
#end
order by id DESC