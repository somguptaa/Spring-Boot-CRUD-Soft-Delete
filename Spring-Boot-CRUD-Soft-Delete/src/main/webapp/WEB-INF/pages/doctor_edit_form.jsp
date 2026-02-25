<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
    <title>Edit Doctor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-warning">
            <h4>Edit Doctor Details</h4>
        </div>

        <div class="card-body">
            <form:form modelAttribute="docDto" action="edit" method="post">

                <form:hidden path="id"/>

                <div class="mb-3">
                    <label class="form-label">Doctor Name</label>
                    <form:input path="name" cssClass="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Specialization</label>
                    <form:input path="specialization" cssClass="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Consultation Fee</label>
                    <form:input path="fee" cssClass="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone Number</label>
                    <form:input path="phone" cssClass="form-control"/>
                </div>

                <button type="submit" class="btn btn-primary">Update Doctor</button>
                <a href="report" class="btn btn-secondary">Cancel</a>

            </form:form>
        </div>
    </div>
</div>

</body>
</html>
