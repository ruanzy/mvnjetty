#sql('count')
	select count(1) from users where 1=1
	#if(name)
	and username = #p(name)
	#end
	and isadmin = 0
#end

#sql('list')
select * from users where 1=1
#if(name)
and username = #p(name)
#end
and isadmin = 0
order by regtime DESC
#end