<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty book.id ? 'Add Book' : 'Edit Book'} — Library</title>
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
        input, select { width:100%; padding:10px 12px; border:1px solid #cbd5e0; border-radius:6px; font-size:.95rem; }
        input:focus, select:focus { outline:none; border-color:#4299e1; box-shadow:0 0 0 3px rgba(66,153,225,.2); }
        .error { color:#e53e3e; font-size:.82rem; margin-top:4px; }
        .alert-error { background:#fed7d7; color:#742a2a; padding:12px; border-radius:6px; margin-bottom:16px; border-left:4px solid #e53e3e; }
        .btn-row { display:flex; gap:12px; margin-top:24px; }
        .btn { padding:10px 24px; border-radius:6px; border:none; cursor:pointer; font-size:.95rem; text-decoration:none; }
        .btn-primary { background:#2b6cb0; color:#fff; }
        .btn-primary:hover { background:#2c5282; }
        .btn-secondary { background:#e2e8f0; color:#4a5568; }
        .btn-secondary:hover { background:#cbd5e0; }
    </style>
</head>
<body>
<header>
    <h1>📚 Library Management System</h1>
    <nav><a href="/books">Books</a><a href="/authors">Authors</a></nav>
</header>
<div class="container">
    <div class="card">
        <h2>${empty book.id ? '➕ Add New Book' : '✏️ Edit Book'}</h2>

        <c:if test="${not empty errorMsg}">
            <div class="alert-error">${errorMsg}</div>
        </c:if>

        <c:set var="action" value="${empty book.id ? '/books/add' : '/books/edit/'.concat(book.id)}"/>
        <form:form action="${action}" method="post" modelAttribute="book">
            <div class="form-group">
                <label>Title *</label>
                <form:input path="title" placeholder="Enter book title"/>
                <form:errors path="title" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>ISBN *</label>
                <form:input path="isbn" placeholder="e.g. 978-0000000000"/>
                <form:errors path="isbn" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>Genre *</label>
                <form:select path="genre">
                    <form:option value="" label="-- Select Genre --"/>
                    <form:option value="Dystopian" label="Dystopian"/>
                    <form:option value="Fantasy" label="Fantasy"/>
                    <form:option value="Literary" label="Literary"/>
                    <form:option value="Historical" label="Historical"/>
                    <form:option value="Satire" label="Satire"/>
                    <form:option value="Magical Realism" label="Magical Realism"/>
                    <form:option value="Modernist" label="Modernist"/>
                    <form:option value="Science Fiction" label="Science Fiction"/>
                    <form:option value="Mystery" label="Mystery"/>
                </form:select>
                <form:errors path="genre" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>Publish Year</label>
                <form:input path="publishYear" type="number" placeholder="e.g. 2005"/>
                <form:errors path="publishYear" cssClass="error"/>
            </div>
            <div class="form-group">
                <label>Price ($)</label>
                <form:input path="price" type="number" step="0.01" placeholder="e.g. 14.99"/>
            </div>
            <div class="form-group">
                <label>Author *</label>
                <select name="authorId">
                    <option value="">-- Select Author --</option>
                    <c:forEach var="a" items="${authors}">
                        <option value="${a.id}"
                            <c:if test="${a.id == selectedAuthorId}">selected</c:if>>
                            ${a.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="btn-row">
                <button type="submit" class="btn btn-primary">
                    ${empty book.id ? 'Add Book' : 'Update Book'}
                </button>
                <a href="/books" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
