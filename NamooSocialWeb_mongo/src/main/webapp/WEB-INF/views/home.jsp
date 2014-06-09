<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>NamooSocial for Namoosori members</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
</head>

<body>

<div class="wrapper">
	<div class="box">
		<div class="row row-offcanvas row-offcanvas-left">

			<!-- sidebar -->
			<div class="column col-sm-2 col-xs-1 sidebar-offcanvas" id="sidebar">

				<ul class="nav">
					<li><a href="#" data-toggle="offcanvas"	class="visible-xs text-center"><i class="glyphicon glyphicon-chevron-right"></i></a></li>
				</ul>

				<c:if test="${isLogin == true}">
				<ul class="nav hidden-xs" id="lg-menu">
					<li class="active"><a href="${ctx}/${loginUser.userId}"><i class="glyphicon glyphicon-list-alt"></i> Timeline</a></li>
					<li><a href="${ctx}/${loginUser.userId}/messages"><i class="glyphicon glyphicon-list"></i> Home</a></li>
					<li><a href="${ctx}/users"><i class="glyphicon glyphicon-user"></i> Members</a></li>
				</ul>
				</c:if>
				<ul class="list-unstyled hidden-xs" id="sidebar-footer">
					<li><a href="http://www.nextree.co.kr"><h3>by Namoosori</h3> <i class="glyphicon glyphicon-heart-empty"></i> Home Blog</a></li>
				</ul>

				<!-- tiny only nav-->
				<ul class="nav visible-xs" id="xs-menu">
					<li><a href="#featured" class="text-center"><i class="glyphicon glyphicon-list-alt"></i></a></li>
					<li><a href="#stories" class="text-center"><i class="glyphicon glyphicon-list"></i></a></li>
					<li><a href="#" class="text-center"><i class="glyphicon glyphicon-paperclip"></i></a></li>
					<li><a href="#" class="text-center"><i class="glyphicon glyphicon-refresh"></i></a></li>
				</ul>

			</div>
			<!-- /sidebar -->

			<!-- main right col -->
			<div class="column col-sm-10 col-xs-11" id="main">

				<!-- top nav -->
				<div class="navbar navbar-blue navbar-static-top">
					<div class="navbar-header">
						<button class="navbar-toggle" type="button"	data-toggle="collapse" data-target=".navbar-collapse">
							<span class="sr-only">Toggle</span> <span class="icon-bar"></span>
							<span class="icon-bar"></span> <span class="icon-bar"></span>
						</button>
						<a href="#" class="navbar-brand logo">n</a>
					</div>
					<nav class="collapse navbar-collapse" role="navigation">
						<form class="navbar-form navbar-left">
							<div class="input-group input-group-sm" style="max-width: 360px;">
								<input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
								<div class="input-group-btn">
									<button class="btn btn-default" type="submit">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</div>
							</div>
						</form>
						<ul class="nav navbar-nav">
							<li><a href="#"><i class="glyphicon glyphicon-home"></i> 홈</a></li>
							<c:if test="${isLogin == true}">
								<li><a href="#postModal" role="button" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i> 새글 등록</a></li>
							</c:if>
						</ul>

						<c:if test="${isLogin == true}">
							<ul class="nav navbar-nav navbar-right">
								<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i>  ${loginUser.name} 님.</a>
									<ul class="dropdown-menu">
										<li><a href="${ctx}/user/logout">로그아웃</a></li>
										<li><a href="#">회원정보수정</a></li>
									</ul>
								</li>
							</ul>
						</c:if>
						
						<!-- 로그아웃 상태일때만 -->
						<c:if test="${isLogin != true}">
							<form class="navbar-form navbar-right" action="${ctx}/user/login" role="form" method="post"> 
					            <div class="form-group">
					              <input type="text" name="userId" placeholder="Email" class="form-control" required="required">
					            </div>
					            <div class="form-group">
					              <input type="password" name="password" placeholder="Password" class="form-control" required="required">
					            </div>
					            <button type="submit" class="btn btn-success">로그인</button>
					            <a href="${ctx}/user/register" class="btn btn-info">회원가입</a>
					        </form>	
						</c:if>
					</nav>
				</div>
				<!-- /top nav -->

				<div class="padding">
					<div class="full col-sm-9">

						<!-- content -->
						<div class="row">

							<!-- main col left -->
							<div class="col-sm-5">

								<div class="panel panel-default">
									<div class="panel-thumbnail">
										<img src="${ctx}/resources/images/profile.png" class="img-responsive">
									</div>
									<div class="panel-body">
										<p class="lead">${user.name}</p>
										<p>${user.followings.size()} Followings, ${user.followers.size()} Followers, ${user.messages.size()} Posts</p>
									</div>
								</div>

								<div class="well">
									<form class="form-horizontal" action="${ctx}/message/register" role="form" method="post">
										<h4>What's New</h4>
										<div class="form-group" style="padding: 14px;">
											<textarea class="form-control" name="contents" placeholder="Update your status"></textarea>
										</div>
										<button class="btn btn-primary pull-right" type="submit">Post</button>
										<ul class="list-inline">
											<li><a href=""><i class="glyphicon glyphicon-upload"></i></a></li>
											<li><a href=""><i class="glyphicon glyphicon-camera"></i></a></li>
											<li><a href=""><i class="glyphicon glyphicon-map-marker"></i></a></li>
										</ul>
									</form>
								</div>

								<div class="panel panel-default">
									<div class="panel-heading">
										<h4>What Is NamooSocial?</h4>
									</div>
									<div class="panel-body">나무소셜은 나무소리 회원들을 위한 소셜 네트워크 서비스입니다. 
									나무소셜과 함께 회원들간의 정보를 공유하세요. </div>
								</div>



							</div>

							<!-- main col right -->
							<div class="col-sm-7">

								<c:forEach var="message" items="${messages}">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4>${message.writer.name} (<fmt:formatDate value="${message.regDate}" pattern="yyyy-MM-dd HH:mm:ss"/>)</h4>
									</div>
									<div class="panel-body">
										${message.contents}
									</div>
								</div>
								</c:forEach>

							</div>
						</div>
						<!--/row-->

						<div class="row" id="footer">
							<div class="col-sm-6"></div>
							<div class="col-sm-6">
								<p>
									<a href="#" class="pull-right">©Copyright 2014 Namoosori.</a>
								</p>
							</div>
						</div>
					</div>
					<!-- /col-9 -->
				</div>
				<!-- /padding -->
			</div>
			<!-- /main -->

		</div>
	</div>
</div>


<!--post modal-->
<div id="postModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				새 메시지 등록
			</div>
			<form class="form center-block" action="${ctx}/message/register" method="post">
				<div class="modal-body">
						<div class="form-group">
							<textarea class="form-control input-lg" name="contents" autofocus="" placeholder="친구들에게 공유할 내용을 적어주세요."></textarea>
						</div>
				</div>
				<div class="modal-footer">
					<div>
						<button class="btn btn-primary btn-sm" type="submit">등록</button>
						<ul class="pull-left list-inline">
							<li><a href=""><i class="glyphicon glyphicon-upload"></i></a></li>
							<li><a href=""><i class="glyphicon glyphicon-camera"></i></a></li>
							<li><a href=""><i class="glyphicon glyphicon-map-marker"></i></a></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

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