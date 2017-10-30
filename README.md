# httptest
http connection test
h1. Http url connection using using Maven
h3.Steps to perform the test.
1. Download the httptest-1.0-SNAPSHOT-jar-with-dependencies.jar
2. Using the command java -jar "path of the jar file"
3. type the url and you will get the below output.

Success result
https://google.com
 json string :: {"date":"30-Oct-2017 12:58:10","content-length":"-1","url":"https://google.com","status":"200"}
 Error Result
bad://example.com
 json string :: {"error":"invalid url","url":"bad://example.com"}
