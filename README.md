# orinstore
Orin Store

#jwt token
curl client:secret@localhost:9000/oauth/token -d grant_type=password -d username=user -d password=pwd

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>.