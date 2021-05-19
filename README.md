# Java coding testing

Consuming an API using Java + Springboot.

About the decisions taken: I have decided to catch all exceptions and return a standard object to make it easier if we need to push the data to another service. How can we improve this? We can set up a cache if we need that the service fetch the data often, we could store the data into a DB instead of files..

if you use Postman you can use the collection at the root of this repository to check all the availables requests.
