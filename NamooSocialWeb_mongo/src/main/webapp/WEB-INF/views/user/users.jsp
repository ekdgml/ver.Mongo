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
						<a href="${ctx}/${loginUser.userId}" class="navbar-brand logo">n</a>
					</div>
					
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
				</div>
				<!-- /top nav -->

				<div class="padding">
					<div class="full col-sm-9">

						<!-- content -->
						<div class="row">
							
							<!-- content -->
							<h2 id="container">사용자 목록</h2>
							<div class="panel panel-default">
						      <!-- Table -->
						      <table class="table">
						        <thead>
						          <tr>
						            <th>이름</th>
						            <th>아이디</th>
						            <th>이메일</th>
						            <th></th>
						          </tr>
						        </thead>
						        <tbody>
						        
						          <c:forEach var="user" items="${users}">
							          <tr>
							            <td>${user.name}</td>
							            <td>${user.userId}</td>
							            <td>${user.email}</td>
							            <td>
							            	<c:choose>
							            		<c:when test="${user.following == true}">
							            			<form action="${ctx}/user/unfollow" method="post">
							            				<input type="hidden" name="userId" value="${user.userId}" />
											            <button type="submit" class="btn btn-danger">언팔로우</button>
							            			</form>
							            		</c:when>
							            		<c:when test="${user.following == false && user.followed}">
							            			<form action="${ctx}/user/follow" method="post">
							            				<input type="hidden" name="userId" value="${user.userId}" />
										           		<button type="submit" class="btn btn-info">맞팔로우</button>
										           	</form>
							            		</c:when>
							            		<c:otherwise>
							            			<form action="${ctx}/user/follow" method="post">
							            				<input type="hidden" name="userId" value="${user.userId}" />
							            				<button type="submit" class="btn btn-primary">팔로우</button>
						            				</form>
							            		</c:otherwise>
							            	</c:choose>
							            </td>
							          </tr>
						          </c:forEach>
						          
						        </tbody>
						      </table>
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