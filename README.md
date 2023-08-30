## performance notes
### follow these best practices:
All to-many associations use FetchType.LAZY by default, and you should not change that.
All to-one associations use FetchType.EAGER by default, and you should set it to LAZY. 
You can do that by setting the fetch attribute on the @ManyToOne or @OneToOne annotation.

@ManyToOne(fetch=FetchType.LAZY)

### TODO
- 1 create data structure
  - tables
  - populate with data
- 2 create scenarios with FetchTypes
  - generate metrics
- 3 create scenarios with Indexes
  - generate metrics
- 4 create scenarios with Caches
  - enable cache level 1
  - enable cache level 2
  - generate metrics
- 5 create scenarios using batch process
  - generate metrics
- 6 generate reports


DELETE FROM METRICS; 
DELETE FROM BOOK;
DELETE FROM AUTHOR;
DELETE FROM BOOKS_AUTHORS;

## https://www.baeldung.com/hibernate-second-level-cache
# First-Level Cache: Hibernate uses a session-level cache, also known as a first-level cache, to store the data that is 
currently being used by a specific session. When an entity is loaded or updated for the first time in a session, it is 
stored in the session-level cache. Any subsequent request to fetch the same entity within the same session will be 
served from the cache, avoiding a database round-trip. The session-level cache is enabled by default and cannot be 
disabled.
# Second-Level Cache: Hibernate also supports a second-level cache, which is a shared cache across multiple sessions. 
This cache stores data that is frequently used across different sessions, reducing the number of database queries and 
improving the overall performance of the application. The second-level cache can be configured with various caching 
providers, such as Ehcache, Infinispan, and Hazelcast.

#Presentation steps

##Intro
-Today I will talk about Performance in Hibernate
-These are the topics that I will cover in this presentation

#Data Model Design:
Design your data model carefully, considering the relationships between entities and the data types of fields.
Data normalization for reducing data redundancy and ensuring that data is stored in the most efficient way possible.

#Database Indexing:
Ensure that your database tables are properly indexed, especially for columns frequently used in queries.
We can create them by ourselves or creating that using hibernate like the example below (IMAGE WITH EXAMPLE)
-Pending generate metrics with and without

#Primary keys (Sequences)
Depending of the approach selected we may have performance when generating the PK
https://vladmihalcea.com/why-you-should-never-use-the-table-identifier-generator-with-jpa-and-hibernate/
-1 generate metrics with and without
-2 code example

GenerationType.IDENTITY
which uses auto-incremented column to auto generate the primary key

	Hibernate doesn't recommends it and batch INSERTs are disable for this generationType

GenerationType.TABLE
uses a table to manage the ids

	O problema começa quando começamos a escalar a aplicação. O que acontece se duas entidades sejam persistidas ao mesmo tempo? Precisamos, sempre, fazer um lock pessimista da tabela que gera os ids. Não só isso, mas o que acontece se tivermos que usar outras instâncias do banco de dados? Como faremos com essa tabela?

GenerationType.SEQUENCE
O bom de utilizar as sequences é que, pensando em performance, podemos utilizar o batch do JDBC na hora da inserção e atualização.
O problema de utilizar sequences é que nem todos os bancos de dados oferecem suporte a esse tipo de estratégia de geração. Portanto, utilizar essa estratégia aumenta um pouco o acoplamento com o banco utilizado
GenerationType.IDENTITY

	In identity , database is responsible to auto generate the primary key. Insert a row without specifying a value for the ID and after inserting the row, ask the database for the last generated ID.
	
	Hibernate suggests to use the other types, also batch INSERTs are disable for this generationType

GenerationType.TABLE
pessimistic locking
O problema começa quando começamos a escalar a aplicação. O que acontece se duas entidades sejam persistidas ao mesmo tempo? Precisamos, sempre, fazer um lock pessimista da tabela que gera os ids. Não só isso, mas o que acontece se tivermos que usar outras instâncias do banco de dados? Como faremos com essa tabela?

GenerationType.SEQUENCE

	O bom de utilizar as sequences é que, pensando em performance, podemos utilizar o batch do JDBC na hora da inserção e atualização.
	O problema de utilizar sequences é que nem todos os bancos de dados oferecem suporte a esse tipo de estratégia de geração. Portanto, utilizar essa estratégia aumenta um pouco o acoplamento com o banco utilizado

by default Hibernate comes with the following built-in optimizers:

none: every identifier is fetched from the database, so it’s equivalent to the original sequence generator.
hi/lo: it uses the hi/lo algorithm and it’s equivalent to the original seqhilo generator.
pooled: This optimizer uses a hi/lo optimization strategy, but the current in-memory identifiers highest boundary is extracted from an actual database sequence value.
pooled-lo: It’s similar to the pooled optimizer but the database sequence value is used as the current in-memory lowest boundary
https://vladmihalcea.com/hibernate-hidden-gem-the-pooled-lo-optimizer/

GenerationType.UUID

Although the TABLE generator addresses the portability concern, in reality, it performs poorly because it requires emulating a database sequence using a separate transaction and row-level locks. For this reason, the choice is usually between IDENTITY and SEQUENCE.
https://www.baeldung.com/hibernate-identifiers
https://thorben-janssen.com/jpa-generate-primary-keys/
https://ntsim.uk/posts/how-to-use-hibernate-identifier-sequence-generators-properly

#Caching:
We can use cache to remove database round-trips
1- by default hibernate already provides a first level cache (like a session-level).
When we query an entity first time, it is retrieved from database and stored in first level cache associated with hibernate session. If we query same object again with same session object, it will be loaded from cache and no sql query will be executed.
2 - we can even improve that adding a second level cache (like an application-level)
-2 code example

-1 generate metrics with and without



#Fetch Strategies:
#Lazy Loading:
Hibernate supports lazy loading, which loads associated entities only when they are accessed. Use lazy loading strategically to minimize the amount of data fetched from the database.
1- falar sobre fetch types FetchType.LAZY

The JPA documentation recommends using the FetchType.LAZY strategy whenever possible, and the Entity Graph when we need to load an association.

#Avoid N+1 query issue
Moreover, if you forget to JOIN FETCH an EAGER association in a JPQL query, Hibernate will initialize it with a secondary statement, which in turn can lead to N+1 query issues.
Or we can use EntityGraph to dynamically handle that, it can be used in query hints
https://medium.com/geekculture/jpa-entitygraphs-a-solution-to-n-1-query-problem-e29c28abe5fb

#Batch Fetching:
Use batch fetching to load multiple entities in a single query, reducing the number of database round-trips.
1- falar sobre uso de batch e os beneficios
-1 generate metrics with and without
-2 code example
