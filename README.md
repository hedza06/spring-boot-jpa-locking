## Spring Boot and JPA Optimistic Locking Demo
When it comes to enterprise applications, properly managing concurrent access to a database is crucial. 
This means we should be able to handle multiple transactions in an effective and, most importantly, error-proof way.

What's more, we need to ensure that data stays consistent between concurrent reads and updates.
To achieve that, we can use an optimistic locking mechanism provided by Java Persistence API. 

This way, multiple updates made on the same data at the same time do not interfere with each other.


### Optimistic Locking Explained
If you have a row in a relational database table, which could be updated by concurrent transactions or by concurrent 
long conversations, then most probably you should have optimistic locking in place.

In fact, without any locking mechanism in your live system, most probably a silent data loss is happening 
in your data base even right now, at the moment of reading 😱 !

![alt text](/src/main/resources/img/transaction-problem.png)

In most RDBMS the default transaction isolation level is at least read committed (in MySQL is REPEATABLE READ). 
It solves dirty reads phenomena, which guarantees that the transaction reads only committed data by other transactions.

Because of the isolation level and of the concurrent nature of the transactions, the conflict could happen when 
two transactions have been processed simultaneously:
- Both of them are reading the same state of the record;
- Both of them are making different changes;
- One of them will commit earlier, persisting its changes to database;
- The second one will commit a bit later, silently overwriting the data persisted by the previous one.


### The Elegant Optimistic Locking Solution
o protect the entities against the explained concurrency problems, a new attribute version has been added. 
There are different implementations for the type of this attribute, but the most robust one is just a numeric 
counter (in Java it could be Long, Integer, Short or Timestamp).

![alt text](/src/main/resources/img/transaction-solution.png)


### Creating schema and tables
For creating schema and tables you can use file **create-schema-and-tables.sql**
which is located in resources folder.

### Testing
In src/test package you can find ItemServiceOrchestratorTest class. In that 
class there are two test cases:
- Testing item update price without concurrency
- Testing item update price with concurrent transaction (Optimistic locking will occur and transaction will be retried)


### Roadmap
- Pessimistic locking examples
- Demonstration of locking modes


### Contribution/Suggestions
If someone is interested in contribution or have some suggestions please contact me on e-mail hedzaprog@gmail.com.

### Author
Heril Muratović  
Software Engineer

Mobile/Viber/WhatsUp: +38269657962  
E-mail: hedzaprog@gmail.com  
Skype: hedza06  
Twitter: herilmuratovic  
LinkedIn: https://www.linkedin.com/in/heril-muratovi%C4%87-021097132/  
StackOverflow: https://stackoverflow.com/users/4078505/heril-muratovic  


