Dieser Ordner beinhaltet ein Skelett für einen SPL-Compiler in Java.
Im Laufe der 6 Praktikumsaufgaben stellen Sie diesen SPL-Compiler fertig.
Dafür enthält dieses Dokument einige wichtige Hinweise!
Lesen Sie auch das nicht sprachspezifische README, das oberhalb dieses Ordners liegt.


1. Die Ordnerstruktur

Dieser Ordner enthält einen Unterordner 'src'.
Er enthält sämtliche Java-Quelldateien des Skeletts in einzelne packages unterteilt.
Beachten Sie, dass Sie in der Regel nur die Dateien des 'phases'-Package ergänzen müssen.
Alle anderen Dateien beinhalten ausschließlich Librarycode, den Sie benutzen werden.
Die Dateien und Ordner, und ab welcher Aufgabe sie relevant werden, sind im unteren Diagramm kurz beschrieben.
Für Details zu den einzelnen Quelldateien schauen Sie sich bitte die Dokumentation innerhalb der Datei an.

src/main/java/              -- Wurzelordner für alle Java-Quellen, so wie das Buildsystem Maven es verlangt.
    de/thm/mni/compilerbau/ -- Wurzelpackage des Projekts nach Java-Standard
        phases/                 -- Alle Dateien, die von Ihnen ergänzt werden müssen. Die Namen der Unterordner geben das zugehörige Aufgabenblatt an.
            /_01_scanner/       -- Die Definition des Scanners (PA1)
            /_02_03_parser      -- Die Definition des Parsers (PA2 und PA3)
            /_04a_tablebuild    -- Der Code für die Konstruktion der Symboltabellen (PA4 Teil A)
            /_04b_semant        -- Der Code für die semantische Prüfung der Prozedurrümpfe (PA4 Teil B)
            /_05_varalloc       -- Der Code für die Festlegung von Speicherplätzen in Stackframes (PA5)
            /_06_codegen        -- Der Code für die Eco32-Assemblercodegenerierung (PA6)
        /absyn                  -- Die Definitionen des abstrakten Syntaxbaums (AST). Ab PA3
        /table                  -- Die Definitionen der Symboltabelle und ihrer Einträge. Ab PA4 Teil A
        /types                  -- Die Definitionen von SPL-Typgraphen. Ab PA4 Teil A
        /utils        
            /NotImplemented.java    -- Eine kleine Hilfs-Exception. Wird überall da geworfen, wo Sie noch Code zu schreiben haben.
            /SplError.java          -- Eine Exceptionklasse, die Fehler darstellt, die während der Ausführung des Compilers auftreten können. Besonders relevant in PA4!
.gitignore      -- Eine Datei, die der Versionsverwaltung die Dateien auflistet, die ignoriert werden, also nicht dem Repository hinzugefügt werden sollen (z.B. IDE Dateien, Builddateien).
pom.xml         -- Die Projektdatei für das Buildsystem Maven.


2. Softwarevoraussetzungen

Das Projekt setzt mindestens die Javaversion 11 voraus. Die meisten Linuxdistributionen sollten diese in ihrem Packagemanager enthalten.
Unter Linux können Sie Java 11 mit
    sudo apt install openjdk-11-jdk
installieren. Bitte nutzen Sie keine Java-Features, die erst nach Java 11 dazugekommen sind.

Gebaut wird das Projekt mit dem Buildsystem Maven. Maven ist plattformunabhängig, kann also auch unter Windows genutzt werden.
Trotzdem gilt für die Windowsnutzer: Wir empfehlen sehr stark, das Windows Subsystem for Linux zu installieren und einzurichten! Mehr dazu erfahren Sie im README, das oberhalb dieses Ordners liegt.

Die Projektdefinition für ein Mavenprojekt liegt als XML Datei mit dem Namen 'pom.xml' vor. An dieser Datei müssen Sie in der Regel nichts verändern.
Mehr über Maven erfahren Sie auf 'maven.apache.org'.

Zusätzlich werden zum Bauen des Projekts der Scannergenerator 'jflex' und der Parsergenerator 'cup' genutzt.
Diese Tools werden automatisch aus dem Maven-Repository bezogen. Sie müssen sie also nicht selbst installieren.
Sie benötigen lediglich beim erstmaligen Bauen des Projekts eine aktive Internetverbindung.


3. IDE-Nutzung

Wir empfehlen eine IDE zu nutzen. Dafür empfehlen wir IntelliJ von Jetbrains, das Sie ja bereits kennen sollten.

Wichtig: Öffnen Sie mit IntelliJ den Ordner, in dem dieses README und die pom.xml liegen. Also der Ordner mit dem Namen 'java'.
         Öffnen Sie NICHT den Überordner oder den src-Unterordner. Nur so versteht IntelliJ Ihr Projekt und kann normal genutzt werden!

Beim erstmaligen Öffnen des Projektordner muss IntelliJ das Maven-Projekt importieren. Das müssen Sie in der Aufforderung unten rechts bestätigen.
Damit IntelliJ Maven nutzt, um auch den Scanner-/Parsergenerator aufzurufen, müssen Sie IntelliJ noch anweisen, Maven aufzurufen, bevor IntelliJ versucht, das Projekt zu bauen.
Dafür öffnen Sie auf der rechten Seite von IntelliJ das 'Maven' Menü. Öffnen Sie dort die Unterpunkte 'spl' und 'Lifecycle'.
Wählen Sie dann im Kontextmenü nach einem Rechtsklick auf 'compile' den Punkt 'Execute before build' aus.
Anschließend sollten Sie das Projekt regulär mit IntelliJ bauen und ausführen können.

Wichtig: Die Generatoren ('cup' und 'jflex') generieren Javacode, der anschließend normal in den Kompilierprozess mit eingeflochten wird. 
         Den generierten Code finden sie in den Pfaden 'target/generated-sources/[cup/jflex]/'
         

4. Manuelles Bauen und Ausführen

Wenn Sie das Projekt manuell bauen wollen, führen Sie den folgenden Befehl aus:
    mvn package

Dabei sollte im Ordner 'target' die Datei 'spl-0.1.jar' entstanden sein.
Diese können Sie wie jede andere '.jar'-Datei ausführen mit
    java -jar <path/to/jarfile> <arguments>

Alternativ können Sie zur Ausführung 
    mvn exec:java -Dexec.args="<arguments>"
benutzen.

Wenn Ihnen das zu viel Tipparbeit ist, können Sie sich auch ein Shellskript schreiben, das leichter aufzurufen ist ;)
