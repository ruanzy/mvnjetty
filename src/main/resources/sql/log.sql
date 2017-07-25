count
===
select count(1) from logs where 1=1
@if(isNotEmpty(operator)){
and operator = #{operator}
@}
@if(isNotEmpty(begintime)){
and time >= #{begintime}
@}
@if(isNotEmpty(endtime)){
and time <= #{endtime}
@}
@if(isNotEmpty(memo)){
and memo like #{memo}
@}

list
===
select * from logs where 1=1
@if(isNotEmpty(operator)){
and operator = #{operator}
@}
@if(isNotEmpty(begintime)){
and time >= #{begintime}
@}
@if(isNotEmpty(endtime)){
and time <= #{endtime}
@}
@if(isNotEmpty(memo)){
and memo like #{memo}
@}
order by id DESC