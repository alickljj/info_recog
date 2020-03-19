$(".area_div").hover(function() {
	$(this).css("background-color", "#ffff00");
	$(this).children(".span_div").animate({
		height : 40
	});
	$(this).children("img").animate({
		width : 150
	});
	$(this).children(".line_div").animate({
		width : 100
	});
	$(this).children(".line_div").css("border-width", "3");
	$(this).children(".title_div").animate({
		paddingTop : "10px",
		fontSize : "25px"
	});
	$(this).children(".text_div").fadeIn(1000);
}, function() {
	$(this).css("background-color", "white");
	$(this).children(".span_div").animate({
		height : 50
	});
	$(this).children("img").animate({
		width : 170
	});
	$(this).children(".line_div").animate({
		width : 130
	});
	$(this).children(".line_div").css("border-width", "5");
	$(this).children(".title_div").animate({
		paddingTop : "25px",
		fontSize : "30px"
	});
	$(this).children(".text_div").stop();
	$(this).children(".text_div").fadeOut(10);
});
var vue = new Vue({
	el : '#app',
	data : {
		fileData : {},
		upLoadVisible : false,
		fileList : [],
		result : '',
		info : ''
	},
	methods : {
		// 弹框文件上传到服务器按钮事件
		handleUpLoad : function() {
			this.$refs.upload.submit();
		},
		// 上传成功时的判定
		handleSuccess : function(response, file, fileList) {
			if (response.msg != null) {
				vue.upLoadVisible = false;
				this.$refs.upload.clearFiles();
				vue.result = response.msg
			} else {
				this.$message({
					message : response.error.message,
					type : 'error'
				});
				this.$refs.upload.clearFiles();
			}
		},
		// 上传失败时的判定
		handleError : function(err, file, fileList) {
			vue.$message({
				message : err.message,
				type : 'error'
			});
		},
		// 上传文件按钮
		handleBtnUpLoad : function() {
			this.upLoadVisible = true;
		},

	}
})
function doClick() {
	$("#app").css("display", "");
	vue.upLoadVisible = true;
}