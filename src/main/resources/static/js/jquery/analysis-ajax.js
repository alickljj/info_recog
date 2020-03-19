function ais_ajax(obj) {
	var token = getCookie("rememberMe")
	$.ajax({
		url : obj.url,
		data : obj.data || {},
		dataType : obj.datatype || 'json',
		type : obj.type || 'post',
		async : obj.async || true,
		beforeSend : function(request) {
			request.setRequestHeader("X-Authorization", token);
		},
		success : function(res) {
			if (res.success) {
				var dataObj = {};
				if (typeof (res.data) != "undefined" && res.data != "") {
					dataObj = eval("("+res.data+")");
				}
				if (typeof (dataObj.success) == "undefined" || dataObj.success == 1) {
					obj.successFn(dataObj);
				} else {
					if (typeof (obj.failureFn) != "undefined") {
						obj.failureFn(dataObj);
					}
				
					window.global_vue.$message.error(dataObj.message);
				}

			} else if (res.error != null && res.error.status === 100) {
				if (typeof (obj.failureFn) != "undefined") {
					obj.failureFn(dataObj);
				}
			
				window.global_vue.$message.error(res.error.message)
			} else if (res.error != null && res.error.status === 200) {
				if (typeof (obj.failureFn) != "undefined") {
					obj.failureFn(dataObj);
				}
			
				window.global_vue.$message.error(res.error.message)
			}
		},
		error : function(err) {
			console.log(err.responseJSON);
			if (err.status === 209) {
				delCookie("rememberMe");
				window.location.reload();
			} else {
				window.global_vue.$message.error("通信错误");
			}

		}
	})
}
/*
 * function spd_ajax(obj) { var token = getCookie("token") $.ajax({ url :
 * window.global_vue.webParams.spd_url + obj.url, data : obj.data || {},
 * dataType : obj.datatype || 'json', type : obj.type || 'post', async :
 * obj.async || true, success : function(res) { obj.successFn(res); }, error :
 * function(err) { if(err.status === 200){ window.location.reload(); }else{
 * window.global_vue.$message.error("通信错误"); } } }) }
 */
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
/*******************************************************************************
 * function getCookie(cookie_name) { var allcookies = document.cookie; var
 * cookie_pos = allcookies.indexOf(cookie_name); // 索引的长度 //
 * 如果找到了索引，就代表cookie存在， // 反之，就说明不存在。 if (cookie_pos != -1) { //
 * 把cookie_pos放在值的开始，只要给值加1即可。 cookie_pos += cookie_name.length + 1; var
 * cookie_end = allcookies.indexOf(";", cookie_pos); if (cookie_end == -1) {
 * cookie_end = allcookies.length; } var value =
 * unescape(allcookies.substring(cookie_pos, cookie_end)); } return value; }
 ******************************************************************************/
