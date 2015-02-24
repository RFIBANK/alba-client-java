Библиотека для работы c Alba
=============

Библиотека содержит следующие классы:

AlbaClient - позволяет иницировать транзакию и получить статус транзакции.

InitPaymentAnswer - результат инициации транзакции, содержит такие поля как идентификатор транзакции и сессионный ключ для получения статуса транзакции.

TransactionDetails - результат проверки статуса транзакции.

В процессе работы могут сработать два исключения: AlbaTemporaryError и AlbaFatalError.
AlbaTemporaryError - срабатывает, если случиласть временная ошибка.
AlbaFatalError - срабатывает, если ошибка фатальна и не имеет смысла пытаться повторять операцию.


Пример инициации транзакции:

       AlbaClient alba = new AlbaClient("<KEY>");
       InitPaymentAnswer answer = alba.initPayment("mc", 150, "Test Payment", "mail@example.com", "79111111111", null);

Получение статуса транзакции:

       TransactionDetails details = alba.transactionDetails(answer.getSessionKey());
       if (details.equals("payed") || details.equals("success")) {
          // транзакция оплачена
       } else {
          // транзакция не оплачена
       }

Полноценная демонстрация работы библиотеки: https://github.com/RFIBANK/alba-client-android-example