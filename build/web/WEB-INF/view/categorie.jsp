<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : categorie
    Created on : 30-Aug-2017, 2:45:30 PM
    Author     : Mohamed
--%>
<sql:query var="categories" dataSource="jdbc/cafePlusPlus">
    SELECT * FROM categorie
</sql:query>

<sql:query var="selectedCategory" dataSource="jdbc/cafePlusPlus">
    SELECT nom FROM categorie WHERE id = ?
    <sql:param value="${pageContext.request.queryString}"/>
</sql:query>

<sql:query var="categoryProducts" dataSource="jdbc/cafePlusPlus">
    SELECT * FROM produit WHERE categorie_id = ?
    <sql:param value="${pageContext.request.queryString}"/>
</sql:query>

    
    <div class="col-md-3">
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
    
    <div id="" class="panel">
        
    </div>
    
            <div id="categoryLeftColumn">

                <c:forEach var="categories" items="${categories.rows}">

                    <c:choose>
                        <c:when test="${categories.id == pageContext.request.queryString}">
                            <div class="categoryButton" id="selectedCategory">
                                <span class="categoryText">
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

                <div id="categoryRightColumn">
                    <p id="categoryTitle">${selectedCategory.rows[0].nom}</p>

                    <table id="productTable">
                        <c:forEach var="product" items="${categoryProducts.rows}" varStatus="iter">

                            <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                                <td>
                                    <img src="${initParam.cheminImageProduits}${product.nom}.png"
                                         alt="${product.name}">
                                </td>
                                <td>
                                    ${product.name}
                                    <br>
                                    <span class="smallText">${product.description}</span>
                                </td>
                                <td>
                                    &euro; ${product.prix} / unit
                                </td>
                                <td>
                                    <form action="addToCart" method="post">
                                        <input type="hidden"
                                               name="productId"
                                               value="${product.id}">
                                        <input type="submit"
                                               value="add to cart">
                                    </form>
                                </td>
                            </tr>

                        </c:forEach>
                        
                    </table>
                </div>

            </div>

