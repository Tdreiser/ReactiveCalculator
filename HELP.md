#Сервис для обработки скриптов Python 2.7.

Методом http/post передются параметры через тело запроса.
Формат тела запроса:

`{
    "firstFunction": "abs(-30)", 
    "numberOfCalculates": 2,
    "ordered": true,
    "secondFunction": "max(1,2,3)"
}`

1. firstFunction - первая функция в формате String
2. secondFunction - вторая функция в формате String
3. ordered - признак сортированной выдачи:
+ true возвращает  результат
двух функций на каждой итерации только когда обе функции посчитаны 
+ false возвращает результаты функций по мере их поступления
4. numberOfCalculates - колличество итераций.

Так же в файле application.yml можно настроить задержку выдачи 
на каждой итерации в секундах. Параметр: "customDelay"