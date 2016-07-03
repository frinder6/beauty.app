!(function ($, window, document, undefined) {
    'use strict';

    var language = {
        sProcessing: '处理中...',
        sLengthMenu: '_MENU_ 条记录每页',
        sZeroRecords: '没有匹配结果',
        sInfo: '第 _PAGE_ 页 ( 总共 _PAGES_ 页 / _TOTAL_ 条 )',
        sInfoEmpty: '无记录',
        sInfoFiltered: '(由 _MAX_ 项结果过滤)',
        sInfoPostFix: '',
        sSearch: '搜索：',
        sUrl: '',
        sEmptyTable: '表中数据为空',
        sLoadingRecords: '载入中...',
        sInfoThousands: ',',
        oPaginate: {
            sFirst: '首页',
            sPrevious: '上一页',
            sNext: '下一页',
            sLast: '尾页'
        },
        oAria: {
            sSortAscending: ': 以升序排列此列',
            sSortDescending: ': 以降序排列此列'
        }
    };

    // grid defaults
    var defaults = {
        pagingType: 'full',
        language: language,
        autoWidth: true,
        lengthChange: true,
        ordering: true,
        paging: true,
        processing: false,
        scrollX: true,
        searching: true,
        serverSide: false,
        stateSave: true,
        destory: true,
        displayStart: 0,
        dom: '',
        select: {
            style: 'multi',
            selector: 'td:first-child'
        },
        order: [[1, 'asc']],
        pageLength: 10,
        columns: [],
        data: []
    };

    var DGrid = function (element, options) {
        this.$this = element;
        this.$opts = options;
    };


    /**
     * 加载cols与data使用
     * @param options
     */
    var ajaxGrid = function (options) {
        var defaults = {
            type: 'POST',
            url: '',
            data: {},
            dataType: 'JSON',
            async: false,
            success: function (data) {
            },
            error: function (msg) {
                console.error(JSON.stringify(msg));
            }
        };
        var _options = $.extend(true, {}, defaults, options);
        $.ajax(_options);
    };

    /**
     * dataTables默认事件处理
     * @param e
     * @param toolId
     * @param $opts
     */
    var getDefOptions = function (e, toolId, $opts) {
        var opts = {
            columns: [],
            dom: ('<"row"<"' + toolId + '.col-xs-6"><"col-xs-6"f>r>t<"row"<"col-xs-3"l><"col-xs-3"i><"col-xs-6"p>>'),
            initComplete: function () {
                // 添加操作按钮
                e.toolDiv().find(toolId).append($opts.tools);
                // 权限处理
                $(this).Auth();
                // 操作
                if ($opts.ajax) {
                    e.toolDiv().find('div.btn-group').find('a.oper-operate').click(function () {
                        e.operate();
                    });
                }
                // 自定义初始化化回调方法
                if ($opts.initCallback) {
                    $opts.initCallback();
                }
            },
            drawCallback: function (settings) {
                // 页面初始化完毕处理，如重置页面高等
                $(this).Sys();
                // 自定义回调方法
                if ($opts.drawCallback) {
                    $opts.drawCallback();
                }
                // 权限处理
                $(this).Auth();
            },
            fnRowCallback: function (nRow, aData, iDisplayIndex) {
                /*
                 * row特殊处理
                 */
                e.xEdit(nRow, aData);
                return nRow;
            }
        };
        if ($opts.grid.pagingType) {
            opts.pagingType = $opts.grid.pagingType;
        }
        if ($opts.grid.dom) {
            opts.dom = $opts.grid.dom;
        }
        return opts;
    };


    /**
     * 单元格编辑
     * @param $this
     * @param id
     * @param url
     */
    var xedit = function ($this, opt) {
        $this.editable({
            // mode : 'inline',
            defaultValue: '',
            pk: 1,
            showbuttons: false,
            emptytext: '--',
            placement: 'right',
            success: function (response, newValue) {
                var data = {
                    id: opt.id
                };
                data[opt.v] = newValue;
                var params = {
                    data: data,
                    url: opt.url
                };
                ajax(params);
            }
        });
    };

    DGrid.prototype = {
        constructor: DGrid,
        init: function (options) {
            var $opts = this.$opts;
            // load columns
            var load_cols_options = {
                url: _PATH('/columns/load/columns.action'),
                data: {
                    tableName: $opts.gridName
                },
                success: function (data) {
                    $.extend(true, options.columns, data);
                }
            };
            ajaxGrid(load_cols_options);
            // load data
            var load_data_options = $.extend(true, {}, $opts.grid.ajax, {
                success: function (data) {
                    $.extend(true, options.data, data);
                }
            });
            ajaxGrid(load_data_options);
        },
        render: function (options) {
            if (options.columns) {
                $.each(options.columns, function (i, c) {
                    if (c.render) {
                        c.render = eval('(' + c.render + ')');
                    }
                });
            }
        },
        toolDiv: function () {
            var div = $('#' + this.$this.attr('id') + '_wrapper');
            return div;
        },
        checkbox: function (options) {
            var classValue = options.showDetail ? 'select-checkbox details-control' : 'select-checkbox';
            options.columns.unshift({
                orderable: false,
                searchable: false,
                data: null,
                className: classValue,
                defaultContent: '',
                title: '<input class="table-select" type="checkbox" />',
                width: 10
            });
        },
        initEditableCols: function (options) {
            if (options.columns) {
                var edits = [];
                $.each(options.columns, function (i, c) {
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
        xEdit: function (nRow, aData) {
            var edits = this.$edits;
            var $opts = this.$opts;
            var text = '<span>{0}</span>';
            $(edits).each(function (i, edit) {
                $.each(edit, function (k, v) {
                    var val = $(k, nRow).text();
                    $(k, nRow).html(text.format(val));
                    xedit($(k, nRow).find('span'), {id: aData.id, url: $opts.editUrl, v: v});
                });
            });
        },
        myGrid: function () {
            var $opts = this.$opts;
            var $this = this.$this;
            var e = this;
            var toolId = $opts.toolId ? $opts.toolId : ('#' + $opts.gridName + '_tool');
            var defOpts = getDefOptions(e, toolId, $opts);
            // console.log(JSON.stringify(defOpts));
            // combine defaults conf
            var options = $.extend(true, {}, defaults, defOpts);
            // combine the settings
            e.init(options);
            // convert render to object
            e.render(options);
            // checkbox
            e.checkbox(options);
            // 初始化可编辑单元格
            e.initEditableCols(options);
            // console.log(JSON.stringify(options));
            // return table
            var table = $this.DataTable(options);
            // set table for get
            e.$table = table;
            // select
            e.select();
            // change page length
            e.changePageLength();
            // order
            e.orderSearch();
            return $this;
        },
        getTable: function () {
            return this.$table;
        },
        select: function () {
            var e = this;
            var table = e.getTable();
            this.toolDiv().find('input.table-select').click(function () {
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
        changePageLength: function () {
            var e = this;
            var table = e.getTable();
            table.on('length.dt', function () {
                table.rows().deselect();
            });
        },
        orderSearch: function () {
            var e = this;
            var table = e.getTable();
            table.on('search.dt order.dt', function () {
                var ck = e.toolDiv().find('input.table-select');
                ck.attr('checked') == 'checked' ? ck.removeAttr('checked') : '';
                table.rows().deselect();
            });
        },
        addRows: function (items) {
            var e = this;
            var table = e.getTable();
            table.rows().add(items).draw(false);
        },
        reomveRows: function () {
            var e = this;
            var table = e.getTable();
            table.rows('.selected').remove().draw(false);
        },
        selectItems: function () {
            var e = this;
            var table = e.getTable();
            var items = table.rows({
                selected: true
            }).data();
            if (items.length == 0) {
                layer.msg('至少选择一条！', {
                    offset: '120px'
                });
                return;
            }
            return items;
        },
        selectIds: function () {
            var e = this;
            var items = e.selectItems();
            if (!items) {
                return;
            }
            var ids = $.map(items, function (item, i) {
                return item.id;
            });
            return ids;
        },
        getSelectItems: function (key) {
            var e = this;
            var items = e.selectItems();
            if (!items) {
                return;
            }
            var items = $.map(items, function (item, i) {
                return item[key];
            });
            return items;
        },
        operate: function () {
            var e = this;
            var $opts = this.$opts;
            var ids = e.selectIds();
            if (!ids) {
                return;
            }
            var params = $.extend(true, {
                data: {}
            }, $opts.ajax);
            params.data.values = ids.join(',');
            layer.confirm('操作不可逆，确定执行？', {
                offset: '100px'
            }, function () {
                ajax(params, function () {
                    e.reload();
                    if ($opts.extraLoad) {
                        $opts.extraLoad();
                    }
                });
            });
        },
        reload: function () {
            var table = this.getTable();
            var $opts = this.$opts;
            console.log(JSON.stringify($opts))
            var load_data_options = $.extend(true, {}, $opts.grid.ajax, {
                success: function (data) {
                    table.clear().draw();
                    table.rows.add(data).draw();
                }
            });
            ajaxGrid(load_data_options);
        }
    };

    $.fn.DGrid = function (options) {
        var grid = new DGrid(this, options);
        grid.myGrid();
        return grid;
    };

    $.fn.DGrid.Constructor = DGrid;

})(jQuery, window, document);