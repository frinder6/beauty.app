/**
 * frinder_liu
 *
 * @date 2015-08-23 22:07
 */

var _RENDER_OPER = function (data, type, row, meta) {
    var result = '<span class="fa fa-edit pointer auth" data-auth="RES-RESOURCE-UPD" title="更新" data-href="/pages/sys/resource-update.jsp?id={0}" onclick="_S_REDIRECT(this)"></span> ';
    var config = '<span class="fa fa-copy pointer auth" data-auth="RES-RESOURCE-CONF" title="配置" data-href="/pages/sys/resource-conf.jsp?id={0}&name={1}" onclick="_S_REDIRECT(this)"></span>';
    if (row.parentId > 0 && row.type == 1) {
        result += config.format(row.id, row.name);
    }
    return result.format(row.id);
};


$(function () {

    var tools_txt = type == 1 ? '只看资源' : '返回菜单';

    var tools = '<div class="btn-group">\
		<a data-href="/pages/sys/resource-add.jsp" class="btn btn-default fa fa-plus-square-o auth" data-auth="RES-RESOURCE-ADD" onclick="_S_REDIRECT(this)">&nbsp;新增</a>\
		<a class="btn btn-default fa fa-minus-square-o oper-operate auth" data-auth="RES-RESOURCE-DEL">&nbsp;删除</a>\
		<a class="btn btn-default fa fa-filter" onclick="direct({0})">&nbsp;{1}</a>\
    </div>';

    var table = $('#list').DGrid({
        gridName: 'BEAUTY_RESOURCE',
        tools: tools.format(type, tools_txt),
        grid: {
            ajax: {
                url: _PATH('/resource/load/page.action'),
                data: {
                    type: type
                }
            }
        },
        ajax: {
            url: '/resource/remove.action'
        }
    });


    direct = function (type) {
        var url = '/pages/sys/resource-list.jsp?type={0}';
        _S_URL_REDIRECT(url.format(type == 1 ? 0 : 1));
    }


});