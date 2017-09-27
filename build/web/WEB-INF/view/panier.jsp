

<div id="singleColumn">

    
    <div  class="panel panel-primary">
        
        <div style="padding-right: 155px; font-size:100%;" class="panel-heading">
            
            <c:choose>
                <c:when test="${panier.numberOfItems > 1}">
                    <p>Votre panier contient ${panier.numberOfItems} items.</p>
                </c:when>
                <c:when test="${panier.numberOfItems == 1}">
                    <p>Votre panier contient ${panier.numberOfItems} item.</p>
                </c:when>
                <c:otherwise>
                    <p>Votre panier est vide.</p>
                </c:otherwise>
            </c:choose>
                
        </div>
    </div>
    

    <div id="actionBar">
        <%-- clear panier widget --%>
        <c:if test="${!empty panier && panier.numberOfItems != 0}">
            <a href="viewCart?clear=true" class="btn btn-primary">Vider le panier</a>
        </c:if>

       
        <c:set var="value">
             <c:choose>
                <%--if 'selectedCategory' session object exists, send user to previously viewed category --%>
                
                <c:when test="${!empty categorieSelectionne}">
                    categorie
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <a href="${value}" class="btn btn-primary">Continuer l'achat</a>

        <%-- checkout widget --%>
        <c:if test="${!empty panier && panier.numberOfItems != 0}">
            <a href="checkout" class="btn btn-primary">Passer la commande</a>
        </c:if>
    </div>

    <c:if test="${!empty panier && panier.numberOfItems != 0}">

      <h4 id="subtotal" >sous total: &dollar; ${panier.total}</h4>

      <table style="width: 750px; text-align: left;" class="table table-condensed table-striped  table-hover">

        <tr>
            <th>Produit</th>
            <th>Nom</th>
            <th>Prix</th>
            <th>Quantité</th>
        </tr>

        <c:forEach var="panierItem" items="${panier.items}" varStatus="iter">

          <c:set var="produit" value="${panierItem.product}"/>

          <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
            <td>
              <img src="${initParam.cheminImageProduits}${produit.image}.png"
                   alt="${produit.nom}" style="height: 150px">
            </td>

            <td>${produit.nom}</td>

            <td>
                &dollar; ${panierItem.total}
                <br>
                <span class="smallText">( &dollar; ${produit.prix} / unité )</span>
            </td>

            <td>
                <form action="<c:url value='majPanier'/>" method="post">
                    <input type="hidden"
                           name="produitId"
                           value="${produit.id}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${panierItem.quantity}"
                           name="quantite"
                           style="margin:5px">
                    <input type="submit"
                           class="btn btn-default"
                           name="submit"
                           value="Mise à jour">
                </form>
            </td>
          </tr>

        </c:forEach>
                           
        

      </table>

    </c:if>
</div>