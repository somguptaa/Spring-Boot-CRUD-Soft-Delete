<%@ page isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Doctor Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="d-flex justify-content-between mb-3 align-items-center">
        
        <div>
            <a href="./" class="btn btn-secondary me-2">Home</a>
            <a href="add" class="btn btn-success">+ Add Doctor</a>
        </div>

        <h3 class="text-primary m-0">Doctor Report</h3>
        
    </div>

    <c:if test="${not empty resultMsg}">
        <div class="alert alert-success">
            ${resultMsg}
        </div>
    </c:if>

    <div class="card shadow">
        <div class="card-body table-responsive">

            <table class="table table-bordered table-hover text-center">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Specialization</th>
                        <th>Fee</th>
                        <th>Net Fee (18% GST)</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="doc" items="${listDto}">
                        <tr>
                            <td>${doc.id}</td>
                            <td>${doc.name}</td>
                            <td>${doc.specialization}</td>
                            <td>${doc.fee}</td>
                            <td>${doc.netfee}</td>
                            <td>${doc.phone}</td>
                            <td>
                                <a href="edit?no=${doc.id}" class="btn btn-warning btn-sm">Edit</a>
                                <a href="delete?no=${doc.id}" 
                                   onclick="return confirm('Are you sure?')" 
                                   class="btn btn-danger btn-sm">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>

        </div>
    </div>

</div>

</body>
</html>