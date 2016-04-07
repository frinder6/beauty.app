<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app>
<head>
<jsp:include page="../import-css.jsp" />

<title>内容块</title>
</head>
<body style="background-color: #E4E7EA;">

	<div class="btn-group">
		<a href="javascript:history.back();" class="btn btn-default fa fa-backward">&nbsp;返回</a>
	</div>

	<form id="basicForm" action="${basePath }/user/add.action" class="form-horizontal form-bordered" method="post" onsubmit="return false;">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">新增用户</h4>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-3 control-label">用户昵称： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<input type="text" name="name" class="form-control" placeholder="Type please..." required ng-model="remark" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">用户帐号： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<input type="text" name="account" class="form-control" placeholder="Type please..." required />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">密码： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<input type="password" name="password" class="form-control" placeholder="Type please..." required />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">性别： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<div class="rdio rdio-primary">
							<input type="radio" id="male" value="1" name="gender" /> <label for="male">男</label>
						</div>
						<div class="rdio rdio-primary">
							<input type="radio" id="female" value="2" name="gender"> <label for="female">女</label>
						</div>
						<div class="rdio rdio-primary">
							<input type="radio" id="scret" value="0" name="gender" required checked="checked" /> <label for="scret">保密</label>
						</div>
						<label class="error" for="gender"></label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">电话： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<input type="tel" name="mobile" class="form-control" placeholder="Type please..." value="18888888888" required />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">邮箱： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<input type="email" name="email" class="form-control" placeholder="Type please..." required />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">备注： <span class="asterisk">*</span></label>
					<div class="col-sm-6">
						<textarea type="textarea" name="remark" rows="5" class="form-control" placeholder="Type please..." required>{{remark}}</textarea>
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


	<%@include file="../import-js.jsp"%>
	
		<script type="text/javascript">
		$(function(){
			
			var form = $('#basicForm').Form({
				listUrl : '/pages/sys/user-list.jsp'
			});
		});
	</script>

</body>

</html>