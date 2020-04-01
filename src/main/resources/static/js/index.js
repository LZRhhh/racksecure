/**
 * 拍照上传图片
 */
var indexObj = {};

indexObj.tips = "没有检测到设备，请确保开启摄像头。";

// begin 显示摄像头的录像。
navigator.getUserMedia = navigator.getUserMedia ||
    navigator.webkitGetUserMedia ||
    navigator.mozGetUserMedia;

if (navigator.getUserMedia) {
    navigator.getUserMedia({ audio: true, video: { width: 800, height: 450 } },
        function(stream) {
            var video = document.getElementById("index__video");
            video.srcObject = stream;
            console.log("stream active", stream)
            video.onloadedmetadata = function(e) {
                video.play();
            };
        },
        function(err) {
            console.log("The following error occurred: " + err.name);
            document.getElementById("index__tips").innerHTML = indexObj.tips;
        }
    );
} else {
    console.log("getUserMedia not supported");
    document.getElementById("index__tips").innerHTML = indexObj.tips;
}
// end 显示摄像头的录像。


/**
 * 拍照上传按钮的事件响应
 */

indexObj.uploadImg = function(){
    var canvas = document.getElementById("index__canvas");
    var video = document.getElementById("index__video");
    if (0 == video.videoWidth) {
        document.getElementById("index__tips").innerHTML = indexObj.tips;
        return;
    }
    // 让canvas和视频一样宽高。
    var w = video.videoWidth;
    var h = video.videoHeight;
    canvas.width = w;
    canvas.height = h;
    // 把video标签中的画面，画到canvas中。
    var ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, w, h);
    // 把canvas中的图像转换成png图片文件的Base64字符串。
    var imgStr = canvas.toDataURL('image/png').split("base64,")[1];
    // 获得用户ID
    var username = document.getElementById("index__user-id").value;
    axios.post("/api/profile/upload", {"username":username, "imgStr": imgStr})
        .then(function(res){
            // console.log(res);
            alert("上传成功")
        }).catch(function(error){
        console.error(error);
    })
}

