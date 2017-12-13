#sql('rolecount')
	select count(1) from role where 1=1
	#if(name)
	and name = #p(name)
	#end
#end

#sql('rolelist')
	select * from role where 1=1
	#if(name)
	and name = #p(name)
	#end
#end

#sql('listAll')
	select * from role
#end