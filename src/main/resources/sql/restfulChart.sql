count
===
select count(1) from restful_chart where 1=1
@if(isNotEmpty(chart_name)){
and chart_name = #{chart_name}
@}
@if(isNotEmpty(chart_type)){
and chart_type = #{chart_type}
@}
@if(isNotEmpty(create_by)){
and create_by = #{create_by}
@}

list
===
select * from restful_chart where 1=1
@if(isNotEmpty(chart_name)){
and chart_name = #{chart_name}
@}
@if(isNotEmpty(chart_type)){
and chart_type = #{chart_type}
@}
@if(isNotEmpty(create_by)){
and create_by = #{create_by}
@}
order by create_time DESC