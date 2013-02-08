javac -d . src/com/fibertechcws/cashregister/db/*.java
javac -d . src/com/fibertechcws/cashregister/gui/register/*.java
javac -d . src/com/fibertechcws/cashregister/gui/manager/*.java
javac -d . src/com/fibertechcws/cashregister/gui/*.java
javac -d . src/com/fibertechcws/cashregister/*.java
java com.fibertechcws.cashregister.CashRegister
set INPUT=
set /P INPUT=Type input: %=%