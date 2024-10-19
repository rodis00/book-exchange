# BOOK EXCHANGE APP - (still in progress)

Jest to aplikacja umożliwiająca wymianę używanych książek pomiędzy użytkownikami aplikacji. Aby zamówić książkę nowy użytkownik 
musi wnieść swój wkład własny w postaci własnej książki, którą zamieści w serwisie. Zależnie od wyceny książki przez administratorów dostanie adekwatną ilość "monet",
za które może kupować książki. Dla przykładu użytkownik wnosi do systemu książke, która zostanie wyceniona na 3 monety, wtedy może kupować książki o tej równowartości.
Aby zwiększyć ilość "monet" użytkownik musi dodać do systemu stosunkowo więcej swoich prywatnych książek. W aplikacji zastosowany jest system nagradzania poprzez 
"kod polecenia" który generuje się każdemu nowemu użytkownikowi. 
Jeśli dokonamy zamówienia i dodamy "kod polecenia" innego użytkownika, dany użytkownik otrzyma dodatkową monentę w postaci nagrody.

This is an application that allows users to exchange used books with each other. To order a book, a new user must contribute by offering one of their own books, which they will list in the service. Depending on the book's valuation by the administrators, the user will receive an appropriate number of "coins" that can be used to purchase books. For example, if a user contributes a book that is valued at 3 coins, they can then buy books of the same value. To increase the number of "coins," the user must add more of their personal books to the system. The application features a reward system through a "referral code" that is generated for each new user. If a book is ordered and the referral code of another user is entered, that user will receive an additional coin as a reward.

## Used technologies:
 - Java 22
 - Spring boot 3
 - Flyway
 - Swagger
 - PostgreSQL 16
---
> [!Note]
> You need `java 22` or higher and `PostgreSQL 16`

## How to run it?
1. Clone a repository - in your terminal paste this line of code
  ```bash
    git clone https://github.com/rodis00/book-exchange.git
  ```
 Or download [zip](https://github.com/rodis00/book-exchange/archive/refs/heads/main.zip) (don't forget to unzip it)
> [!Warning]
> You need to create postgres database with this configuration:
> - db_name: `book_exchange_db`
> - db_username `postgres`
> - db_password `postgres`

> [!Tip]
> Alternatively, change the configuration in the `application.yml` file of the project

2. Open terminal in project folder, then type
  ```bash
    cd backend && mvnw clean install
  ```
3. Finally type
  ```bash
    cd target && java -jar backend-0.0.1-SNAPSHOT.jar
  ```

4. Now you can open web browser and paste this url:
  ```
    http://localhost:8080/swagger-ui/index.html
  ```
---
# API Endpoints documentation
![book-exchange-api-documentation](https://github.com/user-attachments/assets/69242b10-0e2f-4876-8314-97a71d3f2ac8)

