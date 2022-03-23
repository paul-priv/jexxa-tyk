# jexxa-tyk
Dieses Repo dient als Anleitung um beliebige Jexxa Kontexte mit dem Tyk API Gateway zu verbinden. Es wird vorausgesetzt, dass man Jexxa bereits kennt und weiß, wie man die Tutorials ausführt.

Dieses Tutorial wird mit dem Dashboard gemacht. Hierfür benötigt man eine Kostenlose Trial Version von Tyk.

Wir benutzen für dieses Tutorial die Alles-in-Einem Version von Tyk, sowie den Jexxa Docker Tutorial Stack. 

# Jexxa starten

Man kann hierbei die Jexxa Kontexte entweder lokal oder als Docker Container starten. Für dieses Tutorial benutzen wir den [Jexxa Tutorial Docker Stack](https://github.com/repplix/JexxaTutorials/blob/main/deploy/docker-compose.yml). Wir verwenden 2 Kontexte, HelloJexxa und BookStoreJ.

1) Starte im Tutorial Stack nur die Container `tutorialstack-HelloJexxa-1` und `tutorialstack-BookStoreJ-1`.


# Installieren von Tyk Dashboard

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
   ![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Dashboard_Einschalten.jpg?raw=true)



# API Gateway einrichten

## API Erstellen

Zunächst benötigen wir die lokale IP Adresse. Hierfür geben wir in der Kommandozeile `ipconfig` ein.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Lokale_IP_Adresse_Finden.jpg?raw=true)

Die IPv4 Addresse ist die von unserem Rechner, die wir statt `localhost` bzw. `127.0.0.1` verwenden müssen.
Bei der ersten Ausführung muss man sich als Administrator registrieren. Bei wiederholter Ausführung meldet man sich mit Email und Passwort an.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Erstellen_Tyk_1.jpg?raw=true)
![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Erstellen_Tyk_2.jpg?raw=true)
![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Erstellen_Tyk_3.jpg?raw=true)

Wir konfigurieren beispielshaft eine API für HelloJexxa. Hierfür geben wir bei `Name` den Namen ein, also `hellojexxa` und bei `Target URL` geben wir unsere lokale IP Adresse und den Port auf dem HelloJexxa läuft (Im Stack ist das Port 7501). Anschließend drücken wir auf `Configure API`.

## API Konfigurieren

Für uns sind zunächst 3 Einstellungen relevant.

1) Der `Listen Path` ist was in der URL hinter dem API Gateway steht. In diesem Beispiel würden wir auf HelloJexxa mit `localhost:8080/hellojexxa/` zugreifen können. `localhost:8080` ist unser API Gateway.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Configurieren_Tyk_1.jpg?raw=true)

2) Target ist die IP Adresse und Port auf dem der HelloJexxa Kontext läuft. Benutze hierbei die lokale IP Adresse und nicht `localhost` oder `127.0.0.1`.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Configurieren_Tyk_2.jpg?raw=true)

3) Wir schalten nur für Testzwecke hier die Authentifizierung aus.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/API_Configurieren_Tyk_3.jpg?raw=true)

4) Wiederholen der Schritte für den BookStoreJ Kontext.

## API Testen

Wir überprüfen die Funktionalität der Jexxa Kontexte wie in den Tutorials beschrieben.

[HelloJexxa](https://github.com/repplix/JexxaTutorials/blob/main/HelloJexxa/README.md)

[BookStoreJ](https://github.com/repplix/JexxaTutorials/blob/main/BookStoreJ/README.md)

Hierbei benutzen wir jedoch NICHT die IP Adresse und den Port auf dem der Kontext läuft, sondern die vom API Gateway (`localhost:8080`) mit dazugehörigem listen Path (`/hellojexxa/`).

Für HelloJexxa können wir beispielsweise `http://localhost:8080/hellojexxa/HelloJexxa/greetings` im Browser eingeben.
