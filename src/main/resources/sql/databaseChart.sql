count
===
select count(1) from database_chart left join bi_database_source on database_chart.datasource_id=bi_database_source.id where 1=1
@if(isNotEmpty(datasource_id)){
and datasource_id = #{datasource_id}
@}
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
select database_chart.*,bi_database_source.name from database_chart left join bi_database_source on database_chart.datasource_id=bi_database_source.id where 1=1
@if(isNotEmpty(datasource_id)){
and datasource_id = #{datasource_id}
@}
@if(isNotEmpty(chart_name)){
and chart_name = #{chart_name}
@}
@if(isNotEmpty(chart_type)){
and chart_type = #{chart_type}
@}
@if(isNotEmpty(create_by)){
and create_by = #{create_by}
@}
order by database_chart.create_time DESC