<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : categorie
    Created on : 30-Aug-2017, 2:45:30 PM
--%>
<sql:query var="categories" dataSource="jdbc/cafePlusPlus">
    SELECT * FROM categorie
</sql:query>

<sql:query var="selectedCategory" dataSource="jdbc/cafePlusPlus">
    SELECT nom FROM categorie WHERE id = ?
    <sql:param value="${pageContext.request.queryString}"/>
</sql:query>

<sql:query var="categorieProduits" dataSource="jdbc/cafePlusPlus">
    SELECT * FROM produit WHERE categorie_id = ?
    <sql:param value="${pageContext.request.queryString}"/>
</sql:query>

    
    <div class="col-md-3" style="padding-top:10%;">
        <div class="panel">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach var="categories" items="${categories.rows}">

                    <c:choose>
                        <c:when test="${categories.id == pageContext.request.queryString}">
                            <li class="active"><a href="#">${categories.nom}</a></li>

                        </c:when>
                        <c:otherwise>
                            <li><a href="categorie?${categories.id}">${categories.nom}</a></li>

                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </ul>
        </div>

    </div>
    
                
                <c:forEach var="categories" items="${categories.rows}">

                    <c:choose>
                        <c:when test="${categories.id == pageContext.request.queryString}">
                            <div class="categoryButton" id="selectedCategory">
                                <span class="panel panel-primary">
                                    ${categories.nom}
                                </span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a href="categorie?${categories.id}" class="categoryButton">
                                <div class="categoryText">
                                    ${categories.nom}
                                </div>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
               

                
                        
                    
                    <div  class="panel panel-primary">
                        <div style="padding-right: 155px; font-size: 200%;" class="panel-heading"><strong>${selectedCategory.rows[0].nom}</strong></div>
                    </div>

                    <table style="width: 580px; text-align: left;" class="table table-condensed table-striped  table-hover">
                        <c:forEach var="produit" items="${categorieProduits.rows}" varStatus="iter">

                            <tr style="height: 100px" class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'} ">
                                <td>
                                    <img src="${initParam.cheminImageProduits}${produit.image}.png"
                                         alt="${produit.nom}">
                                </td>
                                <td>
                                    <strong>${produit.nom}</strong>
                                    <br>
                                    <span class="smallText">${produit.description}</span>
                                </td>
                                <td class="text-align: left;">
                                    ${produit.prix}$ /unité
                                </td>
                                <td>
                                    <form action="addToCart" method="post" >
                                        <input type="hidden"
                                               name="productId"
                                               value="${produit.id}">
                                        <input type="submit" class="btn btn-primary"
                                               value="Ajouter au panier">
                                    </form>
                                </td>
                            </tr>

                        </c:forEach>
                        
                    </table>
                

            

