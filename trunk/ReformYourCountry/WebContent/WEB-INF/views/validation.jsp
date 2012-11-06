<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<c:choose>
    <c:when test="${result eq 'VALID_AND_LOGGED' }">
        Votre compte utilisateur a été validé avec succès. Vous pouvez à présent l'utiliser. Vous venez d'être connecté automatiquement.
    </c:when>
    <c:when test="${result eq 'VALID_BUT_NOT_LOGGED' }">
        Votre compte utilisateur a été validé avec succès. Vous pouvez à présent l'utiliser. Cependant, il ne nous a pas été possible de vous connecter automatiquement et nous vous invitons à le faire manuellement XXXXXXX  LINK LOGIN
    </c:when>
    <c:when test="${result eq 'ALREADY_VALIDATED' }">
        Votre compte utilisateur avait déjà été validé dans le passé. Vous pouvez vous connecter manuellement <a href ="login">ici</a>
        
    </c:when>
    <c:when test="${result eq 'INVALID_CODE' }">
        Votre compte n'a <span class="error">PAS</span> été validé. Vous avez transmis le code suivant via l'URL: '${code}'<br/>
        Veuillez utiliser le lien fournit dans votre e-mail d'inscription (en cliquant sur le lien ou en le copiant en entier dans la barre d'adresse de votre navigateur).
    </c:when>
</c:choose>