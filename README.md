<h1 align="center">Инструкция по запуску консольного приложения, формирующего чек в магазине</h1>
1.  Для начала необходимо скачать проект. Сделать это поможет командная строка(или терминал) и команда:
   
        git clone -b feature/entry-core https://github.com/ManakhauYauheni/clevertec-check.git

     Для удобства проект сразу можно сохранить в заранее специально созданную папку, либо всё будет сделано за вас:
    
        git clone -b feature/entry-core https://github.com/ManakhauYauheni/clevertec-check.git  -Имя папки-(без тире)


ВАЖНО!!! Папка должна быть полностью пустой

      
2.  Далее необходимо при помощи командной строки(или терминала) и команды cd переместиться в директорию проекта в зависимости от его нахождения на вашем компьютере, поэтому настоятельно рекомендую создать предварительно папку для простоты перемещения:

        cd -Имя папки-(всё так же без тире)

3.  Следующим шагом является сборка приложения. Сделать это можно при помощи команды:
   
        gradlew bild

Если сборка не была начата, то попробуйте применить следующий вид команды: 

        .\gradlew build

4.  Последним шагом является запуск приложения. Для этого примените команду следующего вида:
  
        java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
    
 где: 
 
        id-quantity - номер товара и его количество
 
        discountCard=xxxx  - дисконтная карта со своим номером
        
        balanceDebitCard=xxxx - Баланс на счёте расчётной карты

        
 Уважительная просьба!!! В качестве разделителя при указании баланса использовать точку!!!

 
   Ниже представлен пример запуска:

   
        java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-5 2-3 4-1 discountCard=1111 balanceDebitCard=100

Надеюсь, эта инструкция помогла вам  запустить приложение. Если у вас возникнут вопросы или трудности, прошу обратиться по почте ниже.

Почта для связи: manakhov.00@mail.ru

<h2 align="center">##Instructions for running a console application that generates a receipt in the store</h2>



1.First, you need to download the project. To do this, use the terminal and the command:

    git clone https://github.com/ManakhauYauheni/clevertec-check/tree/entry-core.git

For convenience, the project can be saved in a pre-created folder, or everything will be done for you:

    git clone https://github.com/ManakhauYauheni/clevertec-check/tree/entry-core.git -Folder name-(without dashes)


IMPORTANT!!! The folder must be completely empty.


Next, using the terminal and the cd command, move to the project directory depending on its location on your computer, so I strongly recommend creating a folder for ease of movement beforehand:

    cd -Folder name-(without dashes)

3.The next step is to build the application. This can be done using the command:

    gradlew build
                      
If the build does not start, try using the following command format: 

    .\gradlew build

4.The final step is to run the application. To do this, use a command like the following:

    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx

where: 

    id-quantity - the number of the item and its quantity
   
    discountCard=xxxx - discount card with its number

    balanceDebitCard=xxxx - Balance on the account of the debit card


Please use a period as a decimal separator when specifying the balance!!!


Below is an example of running:
java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-5 2-3 4-1 discountCard=1111 balanceDebitCard=100

I hope this instruction helped you to run the application. If you have any questions, please contact me at the email below.

Contact email: manakhov.00@mail.ru
