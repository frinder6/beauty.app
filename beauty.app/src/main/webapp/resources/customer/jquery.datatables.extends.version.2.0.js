!(function($, window, document, undefined) {
	'use strict';

	var language = {
		sProcessing : '处理中...',
		sLengthMenu : '_MENU_ 条记录每页',
		sZeroRecords : '没有匹配结果',
		sInfo : '第 _PAGE_ 页 ( 总共 _PAGES_ 页 / _TOTAL_ 条 )',
		sInfoEmpty : '无记录',
		sInfoFiltered : '(由 _MAX_ 项结果过滤)',
		sInfoPostFix : '',
		sSearch : '搜索：',
		sUrl : '',
		sEmptyTable : '表中数据为空',
		sLoadingRecords : '载入中...',
		sInfoThousands : ',',
		oPaginate : {
			sFirst : '首页',
			sPrevious : '上一页',
			sNext : '下一页',
			sLast : '尾页'
		},
		oAria : {
			sSortAscending : ': 以升序排列此列',
			sSortDescending : ': 以降序排列此列'
		}
	};

	// grid defaults
	var defaults = {
		pagingType : 'full',
		language : language,
		autoWidth : true,
		lengthChange : true,
		ordering : true,
		paging : true,
		processing : true,
		scrollX : true,
		searching : true,
		serverSide : true,
		stateSave : true,
		destory : false,
		displayStart : 0,
		dom : '',
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},
		order : [ [ 2, 'asc' ] ],
		pageLength : 10,
		columns : []
	// orderMulti : false,
	// orderFixed : [ 2, 'asc' ],
	// lengthMenu : [],
	// orderCellsTop : false,
	// orderClasses : true,
	// deferLoading : false,
	// scrollY : 500,
	// deferRender : true,
	// info : true,
	// jQueryUi : false,
	// scrollCollapse : false,
	// searchCols : [],
	// searchDelay : 0,
	// renderer : '',
	// retrieve : '',
	// stateDuration : 0,
	// stripeClasses : '',
	// tabIndex : 0
	};

	var DGrid = function(element, options) {
		this.$this = element;
		this.$opts = options;
	};

	DGrid.prototype = {
		constructor : DGrid,
		init : function(options) {
			var e = this;
			$.ajax({
				type : 'POST',
				url : (_BASE + '/columns/load/columns.action'),
				data : {
					tableName : this.$opts.gridName
				},
				dataType : 'JSON',
				async : false,
				success : function(data) {
					e.fillSearch(data);
					// console.log(JSON.stringify(data));
					$.extend(true, options.columns, data);
				},
				error : function(msg) {
					console.error(JSON.stringify(msg));
				}
			});
		},
		fillSearch : function(datas) {
			var $opts = this.$opts;
			var unfill = $opts.unfill;
			if (unfill) {
				$('form.search-frm').remove();
				return;
			}
			var ldatas = [];
			$.each(datas, function(i, data) {
				var searchable = data.searchable;
				if (searchable) {
					ldatas.push(data);
				}
			});
			var fgroups = [];
			var fgroup = '<div class="form-group">{0}</div>';
			var linput = '\
				<label class="col-sm-1 control-label">{0}：</label>\
	            <div class="col-sm-3">\
	              <input type="text" name="{1}" class="form-control input-sm" />\
	            </div>\
			';
			var len = ldatas.length, size = 3, index = 0;
			var page = parseInt((len + size - 1) / size);
			for (var i = 0; i < page; i++) {
				var linputs = [];
				for (var j = 0; j < size; j++) {
					var data = ldatas[index];
					if (data) {
						linputs.push(linput.format(data.title, data.columnName));
						index++;
					} else {
						break;
					}
				}
				fgroups.push(fgroup.format(linputs.join('')));
			}
			var html = fgroups.join('');
			$(document).find('.search-div').html(html);
		},
		search : function() {
			var e = this;
			var table = e.getTable();
			$(window.document).find('.btn-primary').click(function() {
				table.draw();
			});
		},
		render : function(options) {
			if (options.columns) {
				$.each(options.columns, function(i, c) {
					if (c.render) {
						c.render = eval('(' + c.render + ')');
					}
				});
				// alert(JSON.stringify(options.columns));
			}
		},
		pdiv : function() {
			var div = $('#' + this.$this.attr('id') + '_wrapper');
			return div;
		},
		checkbox : function(options) {
			var classValue = options.showDetail ? 'select-checkbox details-control' : 'select-checkbox';
			options.columns.unshift({
				orderable : false,
				searchable : false,
				data : null,
				className : classValue,
				defaultContent : '',
				title : '<input class="table-select" type="checkbox" />',
				width : 10
			});
		},
		editables : function(options) {
			if (options.columns) {
				var edits = [];
				$.each(options.columns, function(i, c) {
					if (c.editable) {
						var key = 'td:eq({0})'.format(i);
						var edit = {};
						edit[key] = c.data;
						edits.push(edit);
					}
				});
				this.$edits = edits;
			}
		},
		xedit : function(nRow, aData) {
			var edits = this.$edits;
			var $opts = this.$opts;
			// console.log(JSON.stringify(edits));
			var text = '<span>{0}</span>';
			$.each(edits, function(i, edit) {
				$.each(edit, function(k, v) {
					var val = $(k, nRow).text();
					$(k, nRow).html(text.format(val));
					$(k, nRow).find('span').editable({
						// mode : 'inline',
						defaultValue : '',
						pk : 1,
						showbuttons : false,
						emptytext : '--',
						placement : 'right',
						success : function(response, newValue) {
							var data = {
								id : aData.id
							};
							data[v] = newValue;
							var params = {
								data : data,
								url : $opts.editUrl
							};
							// alert(JSON.stringify(params));
							ajax(params);
						}
					});
				});
			});
		},
		detailFormat : function(data, options) {
			if (options.columns) {
				var tableStr = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">{0}</table>';
				var tr = '<tr><td>{0}：</td><td>{1}</td></tr>';
				var trs = '';
				$.each(options.columns, function(i, c) {
					if (i > 0) {
						trs += tr.format(c.title, data[c.data]);
					}
				});
				return tableStr.format(trs);
			}
		},
		showDetail : function(options) {
			var e = this;
			var showDetail = e.$opts.grid.showDetail;
			if (!showDetail) {
				return;
			}
			var table = e.getTable();
			table.on('click', 'td.details-control', function() {
				var tr = $(this).closest('tr');
				var row = table.row(tr);
				if (row.child.isShown()) {
					row.child.hide();
					tr.removeClass('shown');
				} else {
					row.child(e.detailFormat(row.data(), options)).show();
					tr.addClass('shown');
				}
			});
		},
		serialize : function() {
			var frm = $(window.document).find('form');
			var array = frm.serializeArray();
			var serialize = '', temp = ' AND {0} LIKE \'{1}%\' ';
			$(array).each(function() {
				if (this.value && this.value.length > 0) {
					serialize += temp.format(this.name, this.value);
				}
			});
			return serialize;
		},
		initLoadGrid : function() {
			var $opts = this.$opts;
			var $this = this.$this;
			var e = this;
			var toolId = $opts.toolId ? $opts.toolId : ('#' + $opts.gridName + '_tool');
			var _options = {
				columns : [],
				// dom : ('<"row"<"' + toolId +
				// '.col-xs-6"><"col-xs-6"f>r>t<"row"<"col-xs-3"l><"col-xs-3"i><"col-xs-6"p>>'),
				dom : ('<"row"<"' + toolId + '.col-xs-6"><"col-xs-6"f>r>t<"row"<"col-xs-3"l><"col-xs-3"i><"col-xs-6"p>>'),
				initComplete : function() {

					// 添加操作按钮
					e.pdiv().find(toolId).append($opts.tools);

					// 权限处理
					$(this).Auth();

					// 删除方法
					if ($opts.delUrl) {
						e.pdiv().find('div.btn-group').find('a.oper-delete').click(function() {
							e.remove();
						});
					}

					// 操作
					if ($opts.ajax) {
						e.pdiv().find('div.btn-group').find('a.oper-operate').click(function() {
							e.operate();
						});
					}

					if ($opts.initCallback) {
						$opts.initCallback();
					}

				},
				drawCallback : function(settings) {
					// 页面初始化完毕处理，如重置页面高等
					$(this).Sys();

					if ($opts.drawCallback) {
						$opts.drawCallback();
					}

					// 权限处理
					$(this).Auth();
				},
				fnRowCallback : function(nRow, aData, iDisplayIndex) {
					e.xedit(nRow, aData);
					return nRow;
				},
				fnServerParams : function(aoData) {
					// var params = $(window.document).find('form').form2Json();
					// $.extend(true, aoData, params);
					aoData.serialize = e.serialize();
				}
			};

			// combine defaults conf
			var options = $.extend(true, {}, defaults, _options, $opts.grid);

			// combine the settings
			e.init(options);

			// convert render to object
			e.render(options);

			// checkbox
			e.checkbox(options);

			// editable
			e.editables(options);

			// alert(JSON.stringify(options));

			// return table
			var table = $this.DataTable(options);
			//
			e.$table = table;

			// select
			e.select();

			// change page length
			e.changePageLength();

			// order
			e.orderSearch();

			// search
			e.search();

			// show detail
			e.showDetail(options);

			return $this;
		},
		getTable : function() {
			return this.$table;
		},
		select : function() {
			var e = this;
			var table = e.getTable();
			this.pdiv().find('input.table-select').click(function() {
				var checked = $(this).attr('checked');
				if (checked) {
					// 全选
					table.rows().select();
				} else {
					// 取消全选
					table.rows().deselect();
				}
			});
		},
		changePageLength : function() {
			var e = this;
			var table = e.getTable();
			table.on('length.dt', function() {
				table.rows().deselect();
			});
		},
		orderSearch : function() {
			var e = this;
			var table = e.getTable();
			table.on('search.dt order.dt', function() {
				var ck = e.pdiv().find('input.table-select');
				ck.attr('checked') == 'checked' ? ck.removeAttr('checked') : '';
				table.rows().deselect();
			});
		},
		selectItems : function() {
			var e = this;
			var table = e.getTable();
			var items = table.rows({
				selected : true
			}).data();
			if (items.length == 0) {
				layer.msg('至少选择一条！', {
					offset : '120px'
				});
				return;
			}
			return items;
		},
		selectIds : function() {
			var e = this;
			var items = e.selectItems();
			if (!items) {
				return;
			}
			var ids = $.map(items, function(item, i) {
				return item.id;
			});
			return ids;
		},
		getSelectItems : function(key){
			var e = this;
			var items = e.selectItems();
			if (!items) {
				return;
			}
			var items = $.map(items, function(item, i) {
				return item[key];
			});
			return items;
		},
		remove : function() {
			var e = this;
			var $opts = this.$opts;
			var ids = e.selectIds();
			if (!ids) {
				return;
			}
			var params = {
				data : {
					values : ids.join(','),
					value : $opts.value
				},
				url : $opts.delUrl
			};
			layer.confirm('操作不可逆，确定执行？', {
				offset : '100px'
			}, function() {
				ajax(params, function() {
					e.reload();
					if ($opts.extraLoad) {
						$opts.extraLoad();
					}
				});
			});
		},
		operate : function() {
			var e = this;
			var $opts = this.$opts;
			var ids = e.selectIds();
			if (!ids) {
				return;
			}
			var params = $.extend(true, {
				data : {}
			}, $opts.ajax);
			params.data.values = ids.join(',');
			layer.confirm('操作不可逆，确定执行？', {
				offset : '100px'
			}, function() {
				ajax(params, function() {
					e.reload();
					if ($opts.extraLoad) {
						$opts.extraLoad();
					}
				});
			});
		},
		mark : function() {
			var table = this.getTable();
		},
		reload : function() {
			var table = this.getTable();
			table.row('.selected').remove().draw(false);
			table.ajax.reload();
		}
	};

	$.fn.DGrid = function(options) {
		var grid = new DGrid(this, options);
		grid.initLoadGrid();
		return grid;
	};

	$.fn.DGrid.Constructor = DGrid;

})(jQuery, window, document);