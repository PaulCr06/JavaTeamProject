University Management System - Projekt 1

Team: Paul Cracea & Andrei Lisenchi

Hier ist unsere Spring Boot-Anwendung für das erste Projekt. Wir haben das vorgegebene UML-Diagramm für ein Universitätsverwaltungssystem umgesetzt. Aktuell laufen alle Daten In-Memory ohne echte Datenbank.

Architektur

Wir haben das MVC-Pattern angewendet und die Schichten (Model, Repository, Service, Controller) strikt getrennt. Damit der Code übersichtlich bleibt und dem Single-Responsibility-Prinzip entspricht, hat jede Entität (wie Student oder Teacher) ihren eigenen Controller und Service, statt alles in großen Sammelklassen zu bündeln. Die Repositories nutzen vorerst einfache Java-Listen.

Modellerweiterungen

Zusätzlich zum UML-Diagramm haben wir folgende Attribute ergänzt:

Student: birthDate (LocalDate) und email (String)

Teacher: hireDate (LocalDate) und email (String)

Enrollment: enrollmentDate (LocalDate)

Clean Code

Wir haben uns an SOLID und KISS orientiert. Abhängigkeiten werden direkt über Konstruktoren injiziert. Außerdem nutzen wir konsequent PascalCase für Klassen, camelCase für Methoden/Variablen und LocalDate statt Strings für Datumsangaben.
