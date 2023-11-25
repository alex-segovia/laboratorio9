<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="icon" href="iconoInicio.ico">
    <%String rol = (String) request.getAttribute("rol");%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');
        *{
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-size: 20px;
        }

        body{
            background-color: #bebc9b;
            font-family: 'Roboto',sans-serif !important;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-5">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 mb-3 mt-2" style="font-size: 45px; font-weight: bold; font-style: italic; color: black; text-align: center">Esta página está diseñada para Docentes y Decanos. Tú eres un <%=rol%>.</div>
            <div class="col-2"></div>
        </div>
        <br>
        <div class="row">
            <div class="col-4"></div>
            <div class="col-4" style="text-align: center"><a style="text-decoration: none; color: white" href="<%=request.getContextPath()%>"><button class="btn btn-secondary">Regresar</button></a></div>
            <div class="col-4"></div>
        </div>
    </div>
</body>
</html>
