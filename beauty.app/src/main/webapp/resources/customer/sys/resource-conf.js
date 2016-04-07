/**
 * frinder_liu
 * 
 */
$(function() {

	var _grid = {
		ajax : {
			url : _PATH('/re/load/page.action'),
			data : {
				id : id,
				table : 'left'
			}
		},
		pagingType : 'simple'
	};

	var ltools = '<div class="btn-group">\
        <a class="btn btn-default fa fa-copy oper-operate">&nbsp;导入</a>\
    </div>';

	var lopts = _grid;
	lopts.dom = '<"row"<"#BEAUTY_RESOURCE_LEFT_ltool.col-xs-12"f>>t<"row"<"col-xs-6"i><"col-xs-6"p>>';
	var ltable = $('#l-list').MyGrid({
		gridName : 'BEAUTY_RESOURCE_LEFT',
		tools : ltools,
		toolId : '#BEAUTY_RESOURCE_LEFT_ltool',
		grid : lopts,
		ajax : {
			url : '/resource/export.action',
			data : {
				value : id
			}
		},
		extraLoad : function() {
			rtable.reload();
		}
	});

	/**
	 * -------------------------------------表格2相关--------------------------------------------
	 */

	var rtools = '<div class="btn-group">\
     	<a class="btn btn-default fa fa-minus-square-o oper-operate">&nbsp;删除</a>\
    </div>';

	var ropts = _grid;
	ropts.dom = '<"row"<"#BEAUTY_RESOURCE_RIGHT_rtool.col-xs-12"f>>t<"row"<"col-xs-6"i><"col-xs-6"p>>';
	ropts.ajax.data.table = 'right';
	var rtable = $('#r-list').MyGrid({
		gridName : 'BEAUTY_RESOURCE_RIGHT',
		tools : rtools,
		toolId : '#BEAUTY_RESOURCE_RIGHT_rtool',
		grid : ropts,
		ajax : {
			url : '/resource/remove.action'
		},
		extraLoad : function() {
			ltable.reload();
		}
	});

});