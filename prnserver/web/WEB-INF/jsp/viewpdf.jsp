<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<body>
	<div class="srch_zone">
		<form id="searchForm">
			<fieldset>
				<div class="srch_item_row1">
					<div class="srch_item1">
						<div class="fl_left">
							<label>페이지</label> <input id="ipt_pg" value="1" type="text"
								class="ipt_pg" Onkeyup="viewImage(this.value)">&nbsp;/ <label>${log.pageCount}</label>
						</div>
					</div>
				</div>
				<div class=" srch_btn_box2">
					<button id="btn_next" onClick="env_next()" type="button">다음</button>
					<button id="btn_prev" onClick="env_prev()" type="button">이전</button>
					<button onClick="env_list()" type="button">목록으로</button>
				</div>
			</fieldset>
		</form>
	</div>
	<div id="imageContainer">
		<img id="image" class="imageCon"
			src="${pageContext.request.contextPath}/log/scan/viewImage/${log.logId}/1" />
	</div>
	<script type="text/javascript">
		//다음 이벤트
		function env_next() {
			if ($('#ipt_pg').val() == '') {
				$('#ipt_pg').val(0);
			}
			var imageId = parseInt($('#ipt_pg').val()) + parseInt(1);
			$('#ipt_pg').val(imageId);
			viewImage($('#ipt_pg').val());
		}
		//이전 이벤트
		function env_prev() {
			if ($('#ipt_pg').val() == '') {
				$('#ipt_pg').val(2);
			}
			var imageId = parseInt($('#ipt_pg').val()) - parseInt(1);
			$('#ipt_pg').val(imageId);
			viewImage($('#ipt_pg').val());
		}
		//목록으로 이벤트 
		function env_list() {
			$_('/log/scan/index').go();
		}

		function initView() {
			//var imgID = "${logId.ClientID}";
			var objImg = document.getElementById(imgID);
			var screenW = window.innerWidth;
			var screenH = window.innerHeight;

			//objImg.style.width = screenW;
			//objImg.style.height = screenH;

			//var objImgArea = document.getElementById("Container");
			//objImgArea.style.width = screenW / 2;
			//objImgArea.style.height = screenH / 2;

		}
		$(document).ready(function() {
			//로그가 없는경우 뒤로가기 처리한다.
			if ("${log.pageCount}" == '') {
				alert("없는 페이지 입니다.");
				history.back(-1);
			}
		});
		function viewImage(imageId) {
			if ( imageId == 0) {
				alert('없는 페이지 입니다.');
				$('#ipt_pg').val('');
			} else {
				$('#image').attr(
						'src',
						'${pageContext.request.contextPath}/log/scan/viewImage/'
								+ "${log.logId}" + '/' + imageId);
			}
			if ("${log.pageCount}" == imageId) {
			}
		}

		function zoom2x() {
			//var imgID = 'm_imgPage.ClientID%';
			var objImg = document.getElementById(imgID);
			objImg.height = 1600;
		}

		function zoom1x() {
			//var imgID = 'm_imgPage.ClientID';
			var objImg = document.getElementById(imgID);
			objImg.height = 800;
		}
	</script>
</body>