<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Aston REST</title>
</head>
<body style = 'height: 100vh;background: #0d1b2a;color:white;display: flex;flex-flow: column;justify-content: center;align-items: center;' >
    <h1>Welcome!</h1>
    <form action="./users/insert" method="post">
        <p>Login:</p>
        <input type="text" name="login"/>
        <br/>
        <p>Password:</p>
        <input type="text" name="password"/>
        <br/><br/>
        <input type="submit"/>
    </form>
    <footer>
        <small
            >© Copyright,
            <a href="https://github.com/Mr-Dionysus"
                >Denis Reznikov</a
            ></small
        >
    </footer>
</body>
</html>
