<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty author.id ? 'Add Author' : 'Edit Author'} — Library</title>
    <style>
        * { box-sizing:border-box; margin:0; padding:0; }
        body { font-family:'Segoe UI',sans-serif; background:#f0f4f8; color:#333; }
        header { background:#1a365d; color:#fff; padding:16px 32px; display:flex; justify-content:space-between; align-items:center; }
        header h1 { font-size:1.5rem; }
        nav a { color:#90cdf4; text-decoration:none; margin-left:20px; }
        .container { max-width:600px; margin:40px auto; padding:0 20px; }
        .card { background:#fff; border-radius:10px; padding:32px; box-shadow:0 2px 12px rgba(0,0,0,.1); }
        .card h2 { font-size:1.3rem; color:#2d3748; margin-bottom:24px; border-bottom:2px solid #ebf4ff; padding-bottom:10px; }
        .form-group { margin-bottom:18px; }
        label { display:block; font-weight:600; font-size:.9rem; margin-bottom:6px; color:#4a5568; }
        input, select, textarea { width:100%; padding:10px 12px; border:1px solid #cbd5e0; border-radius:6px; font-size:.95rem; }
        textarea { resize:vertical; height:90px; }
        input:focus, textarea:focus { outline:none; border-color:#4299e1; box-shadow:0 0 0 3px rgba(66,153,225,.2); }
        .error { color:#e53e3e; font-size:.82rem; margin-top:4px; }
        .alert-error { background:#fed7d7; color:#742a2a; padding:12px; border-radius:6px; margin-bottom:16px; border-left:4px solid #e53e3e; }
        .btn-row { display:flex; gap:12px; margin-top:24px; }
        .btn { padding:10px 24px; border-radius:6px; border:none; cursor:pointer; font-size:.95rem; text-decoration:none; }
        .btn-primary { background:#2b6cb0; color:#fff; }
        .btn-secondary { background:#e2e8f0; color:#4a5568; }
    </style>
</head>
<body>
<header>
    <h1>📚 Library Management System</h1>
    <nav><a href="/books">Books</a><a href="/authors">Authors</a></nav>
</header>
<div class="container">
    <div class="card">
        <h2>${empty author.id ? '➕ Add New Author' : '✏️ Edit Author'}</h2>

        <c:if test="${not empty errorMsg}">
            <div class="alert-error">${errorMsg}</div>
        </c:if>

        <c:set var="action" value="${empty author.id ? '/authors/add' : '/authors/edit/'.concat(author.id)}"/>
        <form:form action="${action}" method="post" modelAttribute="author">
            <div class="form-group">
                <label>Full Name *</label>
                <form:input path="name" placeholder="Author's full name"/>
                <form:errors path="name" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>Nationality *</label>
                <form:input path="nationality" placeholder="e.g. British, American"/>
                <form:errors path="nationality" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>Birth Year</label>
                <form:input path="birthYear" type="number" placeholder="e.g. 1965"/>
            </div>
            <div class="form-group">
                <label>Bio</label>
                <form:textarea path="bio" placeholder="Short biography..."/>
            </div>
            <div class="btn-row">
                <button type="submit" class="btn btn-primary">
                    ${empty author.id ? 'Add Author' : 'Update Author'}
                </button>
                <a href="/authors" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
