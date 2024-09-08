<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="style.css" rel="stylesheet" type="text/css">
    <title>Aston REST</title>
</head>
<body>
    <h1>Welcome!</h1>
    <form action="./insertData" method="post">
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
