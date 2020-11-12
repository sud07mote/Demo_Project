Technology Stack :

1. Java 8
2. Spring boot
3. Junit,Mokito
4. JPA(Hibernate)
5. Swagger


Swagger URL: http://localhost:8087/swagger-ui.html#

METHOD : 
GET: http://localhost:8087/api/v1/weather/search?zipCode=90201
Input Param:
ZipCode: ZipCode of any Location or Name of any City

OutPut: 
Date:2020-11-13
Type:City(Pune, India)
The Coolest hours(hmm) are: 400,500 with minimum temperature in celcius:19

Example:
1. 
curl -X GET \
  'http://localhost:8087/api/v1/weather/search?zipCode=pune' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 82166ef3-8b65-3cdd-ad18-612309c56728'
  
Output: 
Date:2020-11-13
Type:City(Pune, India)
The Coolest hours(hmm) are: 400,500 with minimum temperature in celcius:19

2. 

curl -X GET \
  'http://localhost:8087/api/v1/weather/search?zipCode=90201' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: ca2d14e1-9592-4a90-f7cb-1345f5bcb9de'
  
Output: 
Date:2020-11-13
Type:Zipcode(90201)
The Coolest hours(hmm) are: 2300 with minimum temperature in celcius:11  



