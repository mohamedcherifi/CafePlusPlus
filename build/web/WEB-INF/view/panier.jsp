

<div id="singleColumn">

    
    <div  class="panel panel-primary">
        
        <div style="padding-right: 155px; font-size:100%;" class="panel-heading">
            
            <c:choose>
                <c:when test="${panier.nbItems > 1}">
                    <p>Votre panier contient ${panier.nbItems} items.</p>
                </c:when>
                <c:when test="${panier.nbItems == 1}">
                    <p>Votre panier contient ${panier.nbItems} item.</p>
                </c:when>
                <c:otherwise>
                    <p>Votre panier est vide.</p>
                </c:otherwise>
            </c:choose>
                
        </div>
    </div>
    

    <div id="actionBar">
        <%-- clear panier widget --%>
        <c:if test="${!empty panier && panier.nbItems != 0}">
            <a href="viewCart?clear=true" class="btn btn-primary">Vider le panier</a>
        </c:if>

        <%-- continue shopping widget 
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category -->
                <c:when test="${!empty categorieSelectionne}">
                    categorie
                </c:when>
                <%-- otherwise send user to welcome page -->
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>--%>

        <a href="${value}" class="btn btn-primary">Continuer l'achat</a>

        <%-- checkout widget --%>
        <c:if test="${!empty panier && panier.nbItems != 0}">
            <a href="checkout" class="btn btn-primary">Passer la commande</a>
        </c:if>
    </div>

    <c:if test="${!empty panier && panier.nbItems != 0}">

      <h4 id="subtotal" >sous total: &dollar; ${panier.soustotal}</h4>

      <table style="width: 750px; text-align: left;" class="table table-condensed table-striped  table-hover">

        <tr>
            <th>Produit</th>
            <th>Nom</th>
            <th>Prix</th>
            <th>Quantité</th>
        </tr>

        <c:forEach var="panierItem" items="${panier.items}" varStatus="iter">

          <c:set var="produit" value="${panierItem.produit}"/>

          <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
            <td>
              <img src="${initParam.cheminImageProduits}${produit.image}.png"
                   alt="${produit.nom}">
            </td>

            <td>${produit.nom}</td>

            <td>
                &dollar; ${panierItem.total}
                <br>
                <span class="smallText">( &dollar; ${produit.prix} / unité )</span>
            </td>

            <td>
                <form action="majPanier" method="post">
                    <input type="hidden"
                           name="produitId"
                           value="${produit.id}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${panierItem.quantite}"
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
                           
        <c:forEach var="cartItem" items="${panier.items}" varStatus="iter">

         <c:set var="produit" value="${panier.produit}"/>

           <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
               <td>
                   <img src="${initParam.cheminImageProduits}${produit.image}.png"
                        alt="${produit.nom}">
               </td>

               <td>${produit.nom}</td>

               <td>
                   &euro; ${panier.total}
                   <br>
                   <span class="smallText">( &dollar; ${produit.prix} / unit )</span>
               </td>
               ...
           </tr>

       </c:forEach>

      </table>

    </c:if>
</div>