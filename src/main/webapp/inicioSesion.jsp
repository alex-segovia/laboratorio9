<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/a2dd6045c4.js" crossorigin="anonymous"></script>
    <title>Inicio de Sesión</title>
    <link rel="icon" href="iconoInicio.ico">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;600;700&display=swap');
        *{
            font-family: 'Poppins',sans-serif;
            margin: 0;
            padding: 0;
        }

        section{
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            min-height: 100vh;
            background: url(fondo1.jpg) no-repeat center;
            background-size: cover;
        }

        .contenedor{
            position: relative;
            width: 400px;
            backdrop-filter: blur(15px);
            border: 2px solid rgba(255,255,255,0.6);
            border-radius: 20px;
            height: 450px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .contenedor h2{
            font-size: 2.3rem;
            color: white;
            text-align: center;
        }

        .input-contenedor{
            position: relative;
            margin: 30px 0;
            width: 300px;
            border-bottom: 2px solid white;
        }

        .input-contenedor label{
            position: absolute;
            top: 50%;
            left: 5px;
            transform: translateY(-50%);
            color: white;
            font-size: 1rem;
            pointer-events: none;
            transition: 0.6s;
            font-weight: bold;
        }

        input:focus ~ label,
        input:valid ~ label{
            top: -5px;
        }

        .input-contenedor input{
            width: 100%;
            height: 50px;
            background-color: transparent;
            border: none;
            outline: none;
            font-size: 1rem;
            padding: -35px 0 5px;
            color: white;
        }

        .input-contenedor i{
            position: absolute;
            color: white;
            font-size: 1.6rem;
            top: 19px;
            right: 8px;
        }
        button{
            width: 100%;
            height: 45px;
            border-radius: 40px;
            background: white;
            border: none;
            font-weight: bold;
            cursor: pointer;
            outline: none;
            font-size: 1rem;
            transition: 0.4s;
            margin-top: 20px;
        }

        button:hover{
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <section>
        <div class="contenedor">
            <div class="formulario">
                <form method="post" action="<%=request.getContextPath()%>/">
                    <h2>Iniciar Sesión</h2>
                    <div class="input-contenedor">
                        <i class="fa-solid fa-envelope"></i>
                        <input id="correo" type="email" name="correo">
                        <label for="correo">Email</label>
                    </div>

                    <div class="input-contenedor">
                        <i class="fa-solid fa-lock"></i>
                        <input id="password" type="password" name="password">
                        <label for="password">Contraseña</label>
                    </div>

                    <%if(request.getSession().getAttribute("errorLogIn")!=null){%>
                        <p style="color: white; font-size: 1rem">Credenciales incorrectas</p>
                        <%request.getSession().removeAttribute("errorLogIn");%>
                    <%}%>

                    <div>
                        <button type="submit">Acceder</button>
                    </div>
                </form>
            </div>
        </div>
    </section>
</body>
</html>