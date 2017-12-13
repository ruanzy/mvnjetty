#sql('count')
	select count(1) from logs where 1=1
	#if(operator)
	and operator = #p(operator)
	#end
	#if(begintime)
	and time >= #p(begintime)
	#end
	#if(endtime)
	and time <= #p(endtime)
	#end
	#if(memo)
	and memo like #p(memo)
	#end
#end

#sql('list')
	select * from logs where 1=1
	#if(operator)
	and operator = #p(operator)
	#end
	#if(begintime)
	and time >= #p(begintime)
	#end
	#if(endtime)
	and time <= #p(endtime)
	#end
	#if(memo)
	and memo like #p(memo)
	#end
	order by id DESC
#end