<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="../import-css.jsp" />


<title>内容块</title>
</head>
<body style="background-color: #E4E7EA;">

	<div class="table-responsive">
		<table id="list" class="table table-bordered mb30" width="100%">
		</table>
	</div>
	<!-- table-responsive -->

	<jsp:include page="../import-js.jsp" />
	<script type="text/javascript">
		var type = '${param.type}';
        if (!type){
            type = 1;
        }
	</script>
	<script src="${basePath }/resources/bracket/js/custom.js"></script>
	<script type="text/javascript" src="${basePath }/resources/customer/sys/resource-list.js"></script>

</body>
</html>