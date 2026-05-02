<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library — Authors</title>
    <style>
        * { box-sizing:border-box; margin:0; padding:0; }
        body { font-family:'Segoe UI',sans-serif; background:#f0f4f8; color:#333; }
        header { background:#1a365d; color:#fff; padding:16px 32px; display:flex; justify-content:space-between; align-items:center; }
        header h1 { font-size:1.5rem; }
        nav a { color:#90cdf4; text-decoration:none; margin-left:20px; }
        .container { max-width:1000px; margin:30px auto; padding:0 20px; }
        .toolbar { display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; }
        .toolbar h2 { font-size:1.3rem; color:#2d3748; }
        .btn { display:inline-block; padding:8px 18px; border-radius:6px; text-decoration:none; font-size:.9rem; cursor:pointer; border:none; }
        .btn-primary { background:#2b6cb0; color:#fff; }
        .btn-warning { background:#d69e2e; color:#fff; }
        .btn-sm { padding:5px 12px; font-size:.82rem; }
        .alert { padding:12px 16px; border-radius:6px; margin-bottom:16px; }
        .alert-success { background:#c6f6d5; color:#276749; border-left:4px solid #38a169; }
        .alert-error   { background:#fed7d7; color:#742a2a; border-left:4px solid #e53e3e; }
        table { width:100%; border-collapse:collapse; background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,.08); }
        thead { background:#2b6cb0; color:#fff; }
        th, td { padding:12px 16px; text-align:left; }
        tbody tr:nth-child(even) { background:#ebf4ff; }
        tbody tr:hover { background:#bee3f8; }
        .bio { max-width:280px; font-size:.85rem; color:#718096; }
    </style>
</head>
<body>
<header>
    <h1>📚 Library Management System</h1>
    <nav><a href="/books">Books</a><a href="/authors">Authors</a></nav>
</header>
<div class="container">
    <div class="toolbar">
        <h2>All Authors</h2>
        <a href="/authors/add" class="btn btn-primary">+ Add Author</a>
    </div>

    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>

    <table>
        <thead>
            <tr><th>#</th><th>Name</th><th>Nationality</th><th>Birth Year</th><th>Bio</th><th>Action</th></tr>
        </thead>
        <tbody>
        <c:forEach var="a" items="${authors}" varStatus="s">
            <tr>
                <td>${s.count}</td>
                <td><strong>${a.name}</strong></td>
                <td>${a.nationality}</td>
                <td>${a.birthYear}</td>
                <td class="bio">${a.bio}</td>
                <td><a href="/authors/edit/${a.id}" class="btn btn-warning btn-sm">Edit</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
