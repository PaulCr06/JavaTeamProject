University Management System 

Teammitglieder
Paul Cracea
Andrei Lisenchi


Controller & Services: Wir haben uns entschieden, für jede Entität einen eigenen Controller und Service zu erstellen (z. B. StudentController, StudentService), anstatt alles in einer großen Datei zu sammeln. Das hält den Code übersichtlich und folgt dem Single-Responsibility-Prinzip.

Repositories: Alle Daten werden vorerst in einfachen Java-Collections (Listen) im Arbeitsspeicher gehalten.

Modellerweiterungen

Um die Anforderungen zu erfüllen, haben wir drei Klassen um neue, realistische Attribute erweitert:

Student: LocalDate birthDate und String email hinzugefügt.

Teacher: LocalDate hireDate und String email hinzugefügt.

Enrollment: LocalDate enrollmentDate hinzugefügt.

Clean Code & Prinzipien

Prinzipien: Wir haben auf KISS und SOLID geachtet. Abhängigkeiten werden sauber über Konstruktoren injiziert.

Konventionen: Klassen nutzen PascalCase, Methoden und Variablen camelCase.

Datentypen: Wie gefordert, nutzen wir java.time.LocalDate für Datumsangaben statt einfacher Strings.
