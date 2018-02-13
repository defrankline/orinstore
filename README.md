# orinstore
Orin Store

#jwt token
curl client:secret@localhost:9000/oauth/token -d grant_type=password -d username=user -d password=pwd

client: testjwtclientid
secret: XY7kmzoNzl100
Non-admin username and password: john.doe and jwtpass
Admin user: admin.admin and jwtpass
Example of resource accessible to all authenticated users: http://localhost:8080/springjwt/cities
Example of resource accessible to only an admin user: http://localhost:8080/springjwt/users


curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/token -d grant_type=password -d username=john.doe -d password=jwtpass