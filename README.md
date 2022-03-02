# jexxa-tyk
Dieses Repo dient als Anleitung um beliebige Jexxa Kontexte mit dem Tyk API Gateway zu verbinden. Es wird vorausgesetzt, dass man Jexxa bereits kennt und weiß, wie man die Tutorials ausführt.

Dieses Tutorial wird zunächst mit dem Dashboard gemacht. Hierfür benötigt man eine Kostenlose Trial Version von Tyk. Ab ca. Mitte März wird diese Dokumentation auf die Open Source Version umgeschrieben.

Wir benutzen für dieses Tutorial die Alles-in-Einem Version von Tyk.

# Einrichten von Tyk Dashboard

1) Git Repo Klonen: `git clone https://github.com/TykTechnologies/tyk-pro-docker-demo.git`
2) Anpassen der Hosts File:
   - Diese befindet sich bei Windows hier: `C:\Windows\System32\drivers\etc\hosts`
   - Füge diese 2 Zeilen am Ende der Datei hinzu:
   - `127.0.0.1 www.tyk-portal-test.com`
   - `127.0.0.1 www.tyk-test.com`
3) Füge der Konfigurationsdatei den Lizenzschlüssel aus der E-Mail hinzu (Diese kann auch in einem Spam Ordner gelandet sein!)
   - Diese befindet sich im Repo hier: `/confs/tyk_analytics.conf`
   - Den Lizenzschlüssel hier einfügen: `"license_key": "{HIER SCHLÜSSEL EINGEBEN OHNE DIESE KLAMMERN}"`
4) Docker Compose ausführen: `docker-compose up`
5) Überprüfen, dass das Dashboard ausgeführt wird
   - Diese URL im Browser eingeben: `127.0.0.1:3000`
   - Falls das nicht funktioniert, schau im dazugehörigen Docker Stack nach, ob der Dashboard Container ausgeführt wird
   ![alt text](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Dashboard_Einschalten.jpg?raw=true)
