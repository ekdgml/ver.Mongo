<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>NamooSocial for Namoosori members</title>
	<%@ include file="/WEB-INF/views/layout/common.jsp" %>
</head>

<body>

<!-- Container ======================================================================================= -->
<div class="container">
    <div class="row">
        <div class="col-lg-12">
		        
			<!-- content -->
			<h2 id="container">회원 가입하기</h2>
			
			<div class="well">
			    <p>회원가입을 위해 아래 내용들을 작성해 주세요.</p>
			    <form class="form-horizontal" action="${ctx}/user/register" method="post">
			        <fieldset>
			            <div class="form-group">
			                <label class="col-lg-2 control-label">아이디</label>
			
			                <div class="col-lg-10">
			                    <input type="text" class="form-control" name="userId" placeholder="아이디" required>
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-lg-2 control-label">이름</label>
			
			                <div class="col-lg-10">
			                    <input type="text" class="form-control" name="name" placeholder="이름" required>
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-lg-2 control-label">이메일</label>
			
			                <div class="col-lg-10">
			                    <input type="text" class="form-control" name="email" placeholder="이메일" required>
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-lg-2 control-label">비밀번호</label>
			
			                <div class="col-lg-10">
			                    <input type="password" class="form-control" name="password" placeholder="비밀번호" required>
			                </div>
			            </div>
			            <div class="form-group">
			                <div class="col-lg-10 col-lg-offset-2">
			                    <button type="submit" class="btn btn-primary">확인</button>
			                    <button class="btn btn-default" onclick="history.back(); return false;">취소</button>
			                </div>
			            </div>
			        </fieldset>
			    </form>
			</div>
		</div>
	</div>
</div>

<script type='text/javascript' src="${ctx}/resources/common/js/jquery-2.1.0.js"></script>
<script type='text/javascript' src="${ctx}/resources/common/js/bootstrap.min.js"></script>

<!-- JavaScript jQuery code from Bootply.com editor -->

<script type='text/javascript'>
$(document).ready(function() {

	/* off-canvas sidebar toggle */
	$('[data-toggle=offcanvas]').click(function() {
		$(this)
				.toggleClass(
						'visible-xs text-center');
		$(this)
				.find('i')
				.toggleClass(
						'glyphicon-chevron-right glyphicon-chevron-left');
		$('.row-offcanvas')
				.toggleClass('active');
		$('#lg-menu').toggleClass(
				'hidden-xs')
				.toggleClass(
						'visible-xs');
		$('#xs-menu').toggleClass(
				'visible-xs')
				.toggleClass(
						'hidden-xs');
		$('#btnShow').toggle();
	});
});
</script>

</body>
</html>