# Tea catalog

Imagine there is one some huge Tea Catalog (check out teaCatalog.json (small data set) and hugeTeaCatalog.json (larger data set) ),
 which contains different statistics how many people enjoy which type of tea.
The main goal is to create Tea Catalog Service, which would be responsible for :

1. Which types of tea are available in particular country
2. Which type of tea is the most popular in particular country
3. Which type of tea is the less popular in particular country
4. Which type of tea is the most famous and popular around the world
5. Which type of tea is the less popular around the world
6. How many people drink particular type of tea around the world
7. Which country drinks more different types of tea
8. Which country drinks least different types of tea
9. How many unique types of tea exist in the catalog
10. How many people drink particular type of tea per country

### Prerequisites

- Java 8
- Maven
- MariaDB
- Gson

### Description

Tea Catalog Service supports two different DAO : in-memory and DB. 

The main flow looks like :
1. At startup, given JSON (teaCatalog.json or hugeTeaCatalog.json) is parsed
2. Data is loaded to maps (in-memory) or to MariaDB
3. Each DAO provides methods to access data to get results on above 10 requirements


DB DAO uses MariaDB and JDBC to access the data. MariaDB is located at Heroku cloud.
In memory DAO uses maps and comparators to mimic the same functionality that could be easily provided by DB



## Authors

* **Liudmyla Zahumna** 