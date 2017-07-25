define(['util', 'toastr'], function(util, toastr){
	var obj = {
		init: function(){
			var me = this;
			util.render('html/rolemgr.html', 'container');
			$('#add').on('click', function(){me.add();});
			me.list();
		},
		list: function(){
			var me = this;
			$('#datalist').grid({
				url : 'role/list',
				pagesize : 10,
				columns: [
					{ header: util.i18n('SYS_ROLE_ROLENAME'), field: 'name'},
					{ header: util.i18n('OPERATE'), field: 'op', render : opRender}
				]
			});
			function nameRender(v, r){
				var name = r.name;
				var namecode = r.namecode;
				return util.i18n('SYS_ROLE_' + namecode);
			}
			function opRender(v, r){
				var id = r.id;
				var op = new Array();
				var openPermission = $('<a href="javascript:void(0);">' + util.i18n('SYS_ROLE_SETPERMISSION') + '</a>');
				openPermission.on('click', function(){
					me.openPermission(id);
				});
				var span = $('<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>');
				var del = $('<a href="javascript:void(0);">' + util.i18n('DELETE') + '</a>');
				del.on('click', function(){
					me.del(id);
				});
				op.push(openPermission, span, del);
				return op;
			}
		},
		add: function(d){
			var me = this;
			var html = util.tpl('html/sys/addrole.html');
        	var d = $.dialog({
        	    title: util.i18n('SYS_ROLE_ADD'),
        	    content: html,
        	    width: 450,
        	    padding: '20px 50px 0 50px',
				buttons: [
			         {
			        	 text: util.i18n('DONE'),
			        	 cls: 'btn-primary',
			        	 click: function () {
		        	    	var flag = me.addvalid();
		        	    	if(flag){
		        	    		me.save(this);
		        	    	}
			             }
			         },
			         {
			        	 text: util.i18n('CANCEL'),
			        	 click: function () {
			        		 this.close();
			             }
			         }
			    ]
        	});
		},
		save: function(d){
			var url = "role/add";
			$('#form').ajaxSubmit({
				url: url,
				type: 'post',  
				dataType: 'json',
				success: function(){
					d.close();
					toastr.success('新增成功!', '提示');
					$('#datalist').grid('reload');
				},
				error: function(){

				}
			});
		},
		addvalid: function(){
			jQuery.validator.addMethod("pattern", function (value, element, params) {
			    if (!params.test(value)) {
			        return false;
			    }
			    return true;
			});
			jQuery.validator.addMethod("unique", function (value, element, params) {
				var ret = false;
				var data = {};
				data[element.name] = value;
				$.ajax({
					url: params,
					type: 'post',
					async: false,
					data: data,
					dataType: 'json',
			        success: function(result){
			        	ret = result;
					}
				});
			    return ret;
			});
			var v = jQuery("#form").validate({
				onkeyup:false,
				onfocusout:false,
				rules: {
					rolename: {
						required: true,
						minlength: 2,
						pattern: /^[a-zA-Z0-9\u4e00-\u9fa5_]{2,10}$/,
						unique: "role/validname"
					}
				},
				messages: {
					rolename: {
						required: util.i18n('SYS_ROLE_VALID_ROLENAME_REQUIRED'),
						minlength: util.i18n('SYS_ROLE_VALID_ROLENAME_MINLENGTH'),
						pattern: util.i18n('SYS_ROLE_VALID_ROLENAME_PATTERN'),
						unique: util.i18n('SYS_ROLE_VALID_ROLENAME_UNIQUE')
					}
				},
				showErrors:function(errorMap, errorList) {
					if(errorList.length > 0){
						var error = errorList[0].message;
						$('#errormsg').html(error);
						$('#error').css('visibility', 'visible');
					}else{
						$('#errormsg').html('');
						$('#error').css('visibility', 'visible');
					}
				}
			});
			var mm = v.form();
			return mm;
		},
		openPermission: function(roleid){
			var me = this;
			var d = $.dialog({
				title: util.i18n('SYS_ROLE_SETPERMISSION'),
        	    content: '<ul id="tree" class="ztree" style="width:100%; height: 350px; overflow:auto;"></ul>',
        	    width: 300,
				buttons: [
			         {
			        	 text: util.i18n('DONE'),
			        	 cls: 'btn-primary',
			        	 click: function () {
							me.setPermission(d, roleid);
			             }
			         },
			         {
			        	 text: util.i18n('CANCEL'),
			        	 click: function () {
			        		 this.close();
			             }
			         }
			    ],
        	    open: function(){
        	    	me.loadpermission(roleid);
        	    }
        	});
		},
		del: function(id){
			$.confirm(util.i18n('SYS_USER_DELETE_CONFIRM'), function(v){
				if(v){
					var url = "role/del";
					var param = {id: id};
					$.ajax({
						url:url,
						type: 'post',
						data: param,
						dataType: 'json',
						success: function(result){
							toastr.success('删除成功!', '提示');
							$('#datalist').grid('reload');
						}
					});
				}
			});
		},	
		loadpermission: function(roleid){
			var setting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					showIcon: false,
					selectedMulti: false
				},
				check: {
					enable: true,
					chkboxType: { "Y" : "ps", "N" : "ps" }
				},
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: ""
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode) {
						var zTree = $.fn.zTree.getZTreeObj("tree");
						if (treeNode.isParent) {
							zTree.expandNode(treeNode);
							return false;
						} else {
							return true;
						}
					}
				}
			};
			var url = "role/getPermission";
			var param = {roleid: roleid};
			$.ajax({
				url:url,
				type: 'post',
				async: false,
				data: param,
				dataType: 'json',
		        success: function(result){
		        	var t = $("#tree");
		        	$.each(result, function(idx, item){
		        		item['name'] = util.i18n('MENU_' + item['namecode']);
		        	});
		        	t = $.fn.zTree.init(t, setting, result);
				}
			});
		},
		setPermission: function(d, roleid){
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes = treeObj.getCheckedNodes(true);
			var ids = [];
			for(var k in nodes){
				ids.push(nodes[k].id);
			}
			var url = "role/setPermission";
			var param = {roleid: roleid, permissions: ids.join()};
			$.ajax({
				url:url,
				type: 'post',
				data: param,
				dataType: 'json',
		        success: function(result){
					d.close();
		        	toastr.success('权限设置成功!', '提示');
				}
			});
		}
	};
	return obj;
});