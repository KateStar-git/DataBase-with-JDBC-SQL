# DataBase-with-JDBC-SQL
A simple program/exercise that simulates the operation of Foreign Key integrity constraints from the Java code level using JDBC calls 
in the event of an attempt to violate data consistency during insertion.

## Stack

*Java 17
*JDBC
*Oracle SQl Developer  Version 22.2.1.234


## Logic
A simple program/exercise using JDBC that:

1. creates a database schema in accordance with the presented E-R diagram (do not define integrity restrictions such as Master Key and Foreign Key in the database).

2. Fill in the TEACHER, STUDENT, SUBJECT, and ASSESSMENT tables with sample data.

3. Enables assessment - filling out the EVALUATION table. Input data in the form of idn, idu, idp, ido and the type of grade ("C" - partial, "S" - semester) should be entered from the console.

