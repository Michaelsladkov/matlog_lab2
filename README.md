# Задание
Написать программу, которая на вход принимает корректное доказательство утверждения `В` в контексте `Г, А`, 
и перестраивает его в корректное доказательство высказывания `A->B` в контексте `Г`.

# Идеи
Получение такого доказательства возможно по теореме о дедукции.
Для перестроения будем применять алгоритм из её доказательства.

## Алгоритм:
1. Разделить в первой строке контекст и результат по оператору `|-`
2. Разделить контекст на гипотезы по `,`
3. Сохранить список гипотез и отдельно гипотезу `A`
4. Распарсить список схем аксиом, сохранить дерево для каждой
5. Считать строку `D_i`
6. Проверить, не содержится ли в строке гипотеза из списка и не соответствует ли строка какой-либо схеме аксиом. Если да, вывести:
   1. `D_i->A->D_i`
   2. `D_i`
   3. `A->D_i`
7. Проверить, является ли строка гипотезой `A`. Если является, вывести:
   1. `A->(A->A)`
   2. `(A->(A->A))->(A->(A->A)->A)->(A->A)`
   3. `(A->(A->A)->A)->(A->A)`
   4. `A->(A->A)->A`
   5. `A->A`
8. Проверить, является ли строка результатом MP. Для этого:
   1. 
   Если является, вывести:
   1. 
   