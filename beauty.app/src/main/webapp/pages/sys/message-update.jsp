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

<form id="basicForm" action="${basePath }/message/update.action" class="form-horizontal" method="post"
      onsubmit="return false;">
    <input type="hidden" name="id" value="${param.id }"/>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">更新消息</h4>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 control-label">发送帐号： <span class="asterisk">*</span></label>
                <div class="col-sm-6">
                    <input type="text" name="account" class="form-control" placeholder="Type please..." required />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">消息头： <span class="asterisk">*</span></label>
                <div class="col-sm-6">
                    <input type="text" name="messageHead" class="form-control" placeholder="Type please..." required  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">消息内容： <span class="asterisk">*</span></label>
                <div class="col-sm-6">
                    <input type="text" name="messageBody" class="form-control" placeholder="Type please..." required ng-model="remark"  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">备注： <span class="asterisk">*</span></label>
                <div class="col-sm-6">
                    <textarea name="remark" rows="5" class="form-control" placeholder="Type please..." required>{{remark}}</textarea>
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

        var id = '${param.id}';


        var form = $('#basicForm').Form({
            listUrl: '/pages/sys/message-list.jsp',
            idUrl: '/message/load/id.action',
            id: id
        });

    });
</script>

</body>

</html>