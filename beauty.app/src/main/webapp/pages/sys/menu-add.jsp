<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html ng-app>
<head>
    <jsp:include page="../import-css.jsp"/>

    <title>内容块</title>
</head>
<body style="background-color: #E4E7EA;">

<div class="btn-group">
    <a href="javascript:history.back();" class="btn btn-default fa fa-backward">&nbsp;返回</a>
</div>

<form id="basicForm" action="${basePath }/resource/add.action" class="form-horizontal" method="post"
      onsubmit="return false;">
    <input type="hidden" name="type" value="1">
    <input type="hidden" name="url" value="/pages/content.jsp">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">新增菜单</h4>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 control-label">菜单名称： <span class="asterisk">*</span></label>

                <div class="col-sm-6">
                    <input type="text" name="name" class="form-control" placeholder="Type please..." required/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">菜单编码： <span class="asterisk">*</span></label>

                <div class="col-sm-6">
                    <input type="text" name="code" class="form-control" placeholder="Type please..." required/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">所属节点： <span class="asterisk">*</span></label>

                <div class="col-sm-6">
                    <select id="pselect" name="parentId" required placeholder="Type please...">
                        <option value="0">/</option>
                    </select> <label class="error" for="pselect"></label>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">备注： <span class="asterisk">*</span></label>

                <div class="col-sm-6">
                    <textarea name="remark" rows="5" class="form-control" placeholder="Type please..."
                              required></textarea>
                </div>
            </div>
        </div>
        <!-- panel-body -->
        <div class="panel-footer">
            <div class="row">
                <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-primary">提交</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </div>
        </div>

    </div>
    <!-- panel -->
</form>


<%@include file="../import-js.jsp" %>

<script type="text/javascript">
    $(function () {

        $('#pselect').Select({
            url: '/menu/select/menu.action',
            remote: true
        });

        $('#basicForm').Form({
            listUrl: '/pages/sys/menu-list.jsp'
        });

    });
</script>

</body>

</html>