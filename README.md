# AnalizatorWydatkow 0.1a

Analizator Wydatków jest aplikacją na platformę Android napisaną w języku Java. Została stworzona w celu pomocy zarządzania swoimi wydakami
w ciągu miesiaca. Aplikacja wykorzystuje baze danych SQlite, w której są zapisywane wszystkie wydatki.

Aplikacja dzieli się na trzy fragmenty zarządzane za pomocą menagera układu ViewPager, są to: 

Wydatki:
Znajdują się tutaj ostatnio dodane wydatki. Do wyświetlenia listy wydatków została zaimplementowana grupa widoku o nazwię RecyclerView. 

Status:
Wyświetlany jest tutaj stan gotówki w ciągu miesiąca oraz tygodnia. Znajduję się tutaj także przycisk do wprowadzania wydatków. 

Statystyki: 
Aplikacja wyświetla miesięczne statystyki na podstawie sumy wydanych pieniędzy w danym dniu. Statystyki są generowane za pomocą
zewnętrznej biblioteki o nazwie MPAndroidChart, udostępnionej na platformie github. 

Aplikacja zawiera rozwijane menu które zawiera następujące opcje:

zresetuj stan,
dodaj gotówke,
nowy tydzień,
nowy miesiąc.

W kolejnym wydaniu AnalizatoraWydatków zostaną dodane/poprawione, następujące elementy: 

funkcja automatycznego przełączania tygodni oraz miesięcy,
wprowadzenie kategorii dla kupowanych produktów,
rozbudowane menu statystyk,
poprawiany layout.

