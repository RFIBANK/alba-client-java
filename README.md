Библиотека для работы c Alba
=============

Библиотека содержит следующие основные классы:

AlbaService - сервис получения доступных способов оплат, инициации транзакции, проверки статуса и т.д.

InitPaymentRequest - параметры запроса для инициации транзакции.

InitPaymentAnswer - результат инициации транзакции, содержит такие поля как идентификатор транзакции и сессионный ключ для получения статуса транзакции.

TransactionDetails - результат проверки статуса транзакции.

В процессе работы могут сработать два исключения: AlbaTemporaryError и AlbaFatalError.
AlbaTemporaryError - срабатывает, если случиласть временная ошибка.
AlbaFatalError - срабатывает, если ошибка фатальна и не имеет смысла пытаться повторять операцию.


Пример инициации транзакции:

       AlbaClient service = new AlbaClient("<KEY>");
       InitPaymentRequest request = new InitPaymentRequest()
                .builder()
                .setPaymentType("mc")
                .setCost(new BigDecimal(10.5))
                .setName("Test")
                .setEmail("main@example.com")
                .setPhone("71111111111")
                .build();
       InitPaymentAnswer answer = service.initPayment(request);

Получение статуса транзакции:

       TransactionDetails details = service.transactionDetails(answer.getSessionKey());
       if (details.getStatus() == TransactionStatus.PAYED || details.getStatus() == TransactionStatus.SUCCESS) {
          // транзакция оплачена
       } else {
          // транзакция не оплачена
       }

Полноценная демонстрация работы библиотеки: https://github.com/RFIBANK/alba-client-android-example
