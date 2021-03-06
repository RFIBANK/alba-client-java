Библиотека для работы c Alba
=============

Библиотека содержит следующие основные классы:

AlbaService - сервис получения доступных способов оплат, инициации транзакции, проверки статуса и т.д.

InitPaymentRequest - параметры запроса для инициации транзакции.

InitPaymentAnswer - результат инициации транзакции, содержит такие поля как идентификатор транзакции и сессионный ключ для получения статуса транзакции.

TransactionDetails - результат проверки статуса транзакции.

RefundRequest - параметры запроса для проведения возврата.

RefundResponse - результат проведения возврата.

CardTokenRequest - параметры запроса на получение токена карточних данных.

CardTokenResponse - результат создания токена.

TransferTypeBank/TransferTypeBankGov - наборы дополнительных параметров для проведения платежей по реквизитам (https://lib.rfibank.ru/pages/viewpage.action?pageId=885366). Включает в себя два основных набора Bank() И BankGov()

В процессе работы могут сработать два исключения: AlbaTemporaryError и AlbaFatalError.
AlbaTemporaryError - срабатывает, если случиласть временная ошибка.
AlbaFatalError - срабатывает, если ошибка фатальна и не имеет смысла пытаться повторять операцию.


Пример инициации транзакции:

       AlbaService service = new AlbaService("<KEY>");
       InitPaymentRequest request = new InitPaymentRequest()
                .builder()
                .paymentType("mc")
                .cost(new BigDecimal(10.5))
                .name("Test")
                .email("main@example.com")
                .phone("71111111111")
                .build();
       InitPaymentResponse response = service.initPayment(request);

Получение статуса транзакции:

       TransactionDetails details = service.transactionDetails(response.getSessionKey());
       if (details.getStatus() == TransactionStatus.PAYED || details.getStatus() == TransactionStatus.SUCCESS) {
          // транзакция оплачена
       } else {
          // транзакция не оплачена
       }

Для проведения оплаты банковской картой с вводом карточных данных необходимо сначала создать токен содержащий данные,
а затем использовать его для инициации платежа. Данные, необходимые для создания токена, содержатся в CardTokenRequest.
Если карта требует проведения 3-D Secure проверку, то InitPaymentResponse будет содержать поле card3ds с данными
для POST запроса на сайт банка-эмитента.

Пример создания токена:

       AlbaService service = new AlbaService("<KEY>");
       CardTokenRequest request = new CardTokenRequest(<ID-Сервиса>, "<Номер карты>", <Месяц>, "<Год>", "<CVC>", "<Владелец карты>");
       CardTokenResponse response = service.createCardToken(request, true); // true - токен для тестовой оплаты

Если токен не удалось создать, то будет либо сгенерировано исключение - в случае проблем авторизации, сетевых проблем и т.д.
Либо метод response.hasErrors() вернет true, в этом случае данные не прошли валидацию,
детализацию ошибок можно найти в response.errors
или вызывая методы getCardErrors, getCardHolderErrors, getExMonthErrors, getExpYearErrors и getCvcErrors.

Инициация транзакции с использованием токена:

       InitPaymentRequest request = InitPaymentRequest.builder()
                    .paymentType("spg_test")
                    .cost(new BigDecimal(10.5))
                    .name("Test")
                    .cardToken(response.getToken())
                    .build();
       InitPaymentResponse response = alba.initPayment(request);

Проверка, требуется ли 3-D Secure:

       Card3ds card3ds = response.getCard3ds();
       if (card3ds != null) {
           // Требуется 3-D secure
       }

Если 3-D secure требуется, то необходимо сделать POST запрос на адрес card3ds.getAcsUrl()
с параметрами:

       PaReq - с значением card3ds.getPaReq()
       MD - с значением card3ds.getMd()
       TermUrl - URL обработчика, на вашем сайте. На него будет возвращён пользователь после прохождения 3DS авторизации на сайте банка-эмитента карты. Этот URL нужно сформировать так, чтобы в нём передавалась информация о транзакции: рекомендуется передавать service_id, tid и order_id (если транзакция создана с ним).

Обработчик результата 3DS авторизации (TermUrl) в GET параметрах получит ранее сформированную информацию о транзакции (service_id, tid, order_id), в POST параметрах получит информацию от банка эмитента - поля PaRes и MD;

Для подтверждения прохождения 3DS авторизации следует вызвать POST запросом API https://partner.rficb.ru/alba/ack3ds/ , передав туда:

        service_id;
        tid или order_id;
        emitent_response - данные, пришедшие от банка-эмитента в виде JSON-encoded словаря (Содержатся в card3ds)

Авторизация запроса: подпись версии 2.0+ или через api_key
Результат метода API /alba/ack3ds/:

1. При неуспехе, будет возвращен ответ в виде JSON:

        {"status": "error", "message": "ОПИСАНИЕ ОШИБКИ"}

2. При успешной проверке, будет возвращен ответ в виде JSON:

        {"status": "success"}


Также есть возможность воспользоваться готовым termUrl, для этого достаточно указать:

         https://secure.rficb.ru/acquire?sid=<ID-Сервиса>&oid=<ID-Транзакции>&op=pay

Или в случае тестовой оплаты:

         https://test.rficb.ru/acquire?sid=<ID-Сервиса>&oid=<ID-Транзакции>&op=pay

Тогда пользователь будет направлен через форму банка, на URL страницы успешной покупки/ошибки сервиса.

Оплата по реквизитам
-------------

Подробная документация - https://lib.rfibank.ru/pages/viewpage.action?pageId=885366
Содержит два набора параметров:

TransferTypeBank - "transfer_type" = "bank";
TransferTypeBankGov - "transfer_type" = "bank_gov";

Создание набора параметров для осуществления платежей по реквизитам:
 
       TransferTypeBank payWith = TransferTypeBank.builder()
				.payerName("Ivan Ivanovich")
				.recipientName("Petr Petrovich")
				.recipientInn("01234567890")
				.recipientAccount("40817810099910004312")
				.recipientBankName("RFI Bank")
				.recipientBankId("044525799")
				.recipientBankCorrespondentAccount("30101810045250000799")
				.build();
       InitPaymentRequest request = InitPaymentRequest.builder()
                     .transferTypeBank(payWith)
                     .build();
                     


Рекуррентный платеж
-------------

Рекуррентные платежи позволяют проводить периодические списания без дополнительного ввода карточных данных.

Первый вызов должен содержать токен, ссылку на подробное описание правил предоставления рекуррентного платежа и
текстовое описание за что производится регистрация РП.

Пример:

         InitPaymentRequest request = InitPaymentRequest
                .builder()
                ...
                .recurrentParams(RecurrentParams.first("<URL>", "<COMMENT>"))
                .cardToken(response.getToken())
                .orderId("1000")
                ...

Последующие вызовы должны содержать orderId, который был использован при первом вызове.

Пример:

         InitPaymentRequest request = InitPaymentRequest
                .builder()
                ...
                .recurrentParams(RecurrentParams.next("1000"))
                ...

Если нужно добавить данные чека для фискализации, то необходимо создать объект InvoiceData и передать его в параметре paymentRequest:

         InvoiceData data = new InvoiceData();
         data.setVatTotal(<стоимость>);

         InvoiceItem item1 = InvoiceItem.builder()
                 .setCode("<Код товара>")
                 .setName("<Наименование товара>")
                 .setUnit("<Единица измерения>")
                 .setVatMode("<Тип НДС>")
                 .setPrice(<Цена за единицу>)
                 .setQuantity(<Количество единиц>)
                 .setSum(<Цена>)
                 .setVatAmount(<Размер НДС>)
                 .build();

         InvoiceItem item2 = InvoiceItem.builder()
                 .setCode("<Код товара>")
                 .setName("<Наименование товара>")
                 .setUnit("<Единица измерения>")
                 .setVatMode("<Тип НДС>")
                 .setPrice(<Цена за единицу>)
                 .setQuantity(<Количество единиц>)
                 .setSum(<Цена>)
                 .setVatAmount(<Размер НДС>)
                 .build();

         ArrayList<InvoiceItem> list = new ArrayList();
         list.add(item1);
         list.add(item2);

         data.setItems(list);

         request.setInvoiceData(data);

Полноценная демонстрация работы библиотеки: https://github.com/RFIBANK/alba-client-android-example

