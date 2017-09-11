<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : index
    Created on : 2017-08-23, 13:45:07
    Author     : A.Sashcov
--%>
<sql:query var="categories" dataSource="jdbc/cafePlusPlus">
    SELECT * FROM categorie
</sql:query>

<!--HEADER PAR JSPF-->

<div class="col-sm-4" >
    <div class="panel panel-default">
        <div class="panel-heading">
            <b>Caf? Plus Plus</b><br />
        </div>
        <div class="panel-body">
            <p>Bienvenue ? notre site web!</p>
            <!-- test to access context parameters -->
            categoryImagePath: ${initParam.categoryImagePath}
            productImagePath: ${initParam.productImagePath}
        </div>
    </div>
</div>

<div class="col-sm-8">
    <div class="panel panel-default">
        <c:forEach var="categories" items="${categories.rows}">
            <div class="panel-heading">
                <a href="view/categorie?${categories.id}">

                    <span class="categoryLabelText">${categories.nom}</span>
            </div>

                            <!--<img src="${initParam.cheminImageCategorie}${categories.image}.png"
                                 alt="${categories.nom}">-->
            </a>

            <div class="panel-body">
                <p><img src="${initParam.cheminImageCategorie}${categories.image}.png"
                        alt="${categories.nom}"></p>
            </div>
    </c:forEach>
    <!--<div class="panel-heading">
        <a href="#">
            <span class="categoryLabelText">Espresso</span>
        </a>
    </div>
    <div class="panel-body">
        <p>Description</p>
    </div>
    <div class="panel-heading">
        <a href="#">
            <span class="categoryLabelText">Caf? Brass?</span>
        </a>
    </div>
    <div class="panel-body">
        <p>Description</p>
    </div>
    <div class="panel-heading">
        <a href="#">
            <span class="categoryLabelText">Caf? Aromatis?</span>
        </a>
    </div>
    <div class="panel-body">
        <p>Description</p>
    </div>
    <div class="panel-heading">
        <a href="#">
            <span class="categoryLabelText">Autres</span>
        </a>
    </div>
    <div class="panel-body">
        <p>Description</p>
    </div>-->
</div>
</div>

<!--FOOTER PAR JSPF-->