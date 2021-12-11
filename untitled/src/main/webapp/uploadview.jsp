<!DOCTYPE html>
<html>
<title>index</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://7npmedia.w3cschool.cn/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<style>
    body,h1 {font-family: "Raleway", sans-serif}
    body, html {height: 100%}
    .bgimg {
        background-image: url('https://atts.w3cschool.cn/forestbridge.jpg');
        min-height: 100%;
        background-position: center;
        background-size: cover;
    }
</style>
<body>

<div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topleft w3-padding-large w3-xlarge">
        JAVA EE
    </div>
    <div class="w3-display-middle">
        <form action="http://localhost:8080/upload/submit" enctype="multipart/form-data" method="post">
            <p class="w3-jumbo w3-animate-top">please choose file</p>
            <%--            上传文件关键句--%>
            <input class="w3-large w3-center" type="file" name="file" value="select">

            <%--            提交--%>
            <input class="w3-large w3-center" type="submit" value="submit">
        </form>
    </div>
</div>
</body>
