<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library — Books</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #333; }
        header { background: #1a365d; color: #fff; padding: 16px 32px; display:flex; justify-content:space-between; align-items:center; }
        header h1 { font-size: 1.5rem; }
        nav a { color: #90cdf4; text-decoration: none; margin-left: 20px; font-size: .95rem; }
        nav a:hover { color: #fff; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; }
        .toolbar { display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; }
        .toolbar h2 { font-size:1.3rem; color:#2d3748; }
        .btn { display:inline-block; padding: 8px 18px; border-radius:6px; text-decoration:none; font-size:.9rem; cursor:pointer; border:none; }
        .btn-primary { background:#2b6cb0; color:#fff; }
        .btn-primary:hover { background:#2c5282; }
        .btn-sm { padding: 5px 12px; font-size:.82rem; }
        .btn-warning { background:#d69e2e; color:#fff; }
        .btn-warning:hover { background:#b7791f; }
        .alert { padding:12px 16px; border-radius:6px; margin-bottom:16px; }
        .alert-success { background:#c6f6d5; color:#276749; border-left:4px solid #38a169; }
        .alert-error   { background:#fed7d7; color:#742a2a; border-left:4px solid #e53e3e; }
        table { width:100%; border-collapse:collapse; background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,.08); }
        thead { background:#2b6cb0; color:#fff; }
        th, td { padding: 12px 16px; text-align:left; }
        tbody tr:nth-child(even) { background:#ebf4ff; }
        tbody tr:hover { background:#bee3f8; }
        .badge { display:inline-block; padding:3px 10px; border-radius:20px; font-size:.78rem; font-weight:600; }
        .badge-dystopian { background:#fbb6ce; color:#702459; }
        .badge-fantasy   { background:#c6f6d5; color:#276749; }
        .badge-literary  { background:#e9d8fd; color:#553c9a; }
        .badge-historical{ background:#fefcbf; color:#744210; }
        .badge-satire    { background:#fed7d7; color:#742a2a; }
        .badge-magical   { background:#b2f5ea; color:#234e52; }
        .badge-modernist { background:#bee3f8; color:#2a4365; }
        .badge-default   { background:#e2e8f0; color:#4a5568; }
    </style>
</head>
<body>
<header>
    <h1>📚 Library Management System</h1>
    <nav>
        <a href="/books">Books</a>
        <a href="/authors">Authors</a>
    </nav>
</header>
<div class="container">
    <div class="toolbar">
        <h2>All Books (with Author Info)</h2>
        <a href="/books/add" class="btn btn-primary">+ Add Book</a>
    </div>

    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>#</th><th>Title</th><th>ISBN</th><th>Genre</th>
                <th>Year</th><th>Price</th><th>Author</th><th>Nationality</th><th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${bookAuthorList}" varStatus="s">
            <tr>
                <td>${s.count}</td>
                <td><strong>${b.bookTitle}</strong></td>
                <td><small>${b.isbn}</small></td>
                <td>
                    <c:set var="genreClass" value="default"/>
                    <c:if test="${b.genre == 'Dystopian'}"><c:set var="genreClass" value="dystopian"/></c:if>
                    <c:if test="${b.genre == 'Fantasy'}"><c:set var="genreClass" value="fantasy"/></c:if>
                    <c:if test="${b.genre == 'Literary'}"><c:set var="genreClass" value="literary"/></c:if>
                    <c:if test="${b.genre == 'Historical'}"><c:set var="genreClass" value="historical"/></c:if>
                    <c:if test="${b.genre == 'Satire'}"><c:set var="genreClass" value="satire"/></c:if>
                    <c:if test="${b.genre == 'Magical Realism'}"><c:set var="genreClass" value="magical"/></c:if>
                    <c:if test="${b.genre == 'Modernist'}"><c:set var="genreClass" value="modernist"/></c:if>
                    <span class="badge badge-${genreClass}">${b.genre}</span>
                </td>
                <td>${b.publishYear}</td>
                <td>$${b.price}</td>
                <td>${b.authorName}</td>
                <td>${b.nationality}</td>
                <td><a href="/books/edit/${b.bookId}" class="btn btn-warning btn-sm">Edit</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
