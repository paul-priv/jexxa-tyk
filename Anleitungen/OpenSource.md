# jexxa-tyk

Dieses Tutorial wird mit der Open Source Version gemacht.

Wir benutzen für dieses Tutorial den Jexxa Docker Tutorial Stack, sowie die gratis Anwendung [Postman](http://www.postman.com) um unsere Requests zu versenden. 

# Postman herunterladen und einrichten
1) [Download](https://www.postman.com/downloads/)
2) Wir erstellen eine neue Collection indem wir auf das + drücken. Diese ist wie ein Ordner für unsere Requests.
  
![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_New_Collection.jpg?raw=true)
 
3) Wir erstellen einen neuen Request.
  
![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_New_Request.jpg?raw=true)
 
4) Wir wählen den neuen Request aus und gehen auf "Body". Hier können wir nun unseren Request eingeben.
 
![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_Request_Body.jpg?raw=true)
 
 # Jexxa starten

Man kann hierbei die Jexxa Kontexte entweder lokal oder als Docker Container starten. Für dieses Tutorial benutzen wir den [Jexxa Tutorial Docker Stack](https://github.com/repplix/JexxaTutorials/blob/main/deploy/docker-compose.yml). Wir verwenden 2 Kontexte, HelloJexxa und BookStoreJ.

1) Starte im Tutorial Stack nur die Container `tutorialstack-HelloJexxa-1` und `tutorialstack-BookStoreJ-1`.

# Installieren von Tyk Open Source

1) Git Repo klonen: `git clone https://github.com/TykTechnologies/tyk-gateway-docker`
2) In den neuen Ordner wechseln: `cd tyk-gateway-docker`
3) Docker Compose ausführen: `docker-compose up -d`
4) Überprüfen ob der API Gateway läuft, indem wir eine Anfrage an `http://localhost:8080/hello` senden. Die Antwort sollte so aussehen:
 
`{"status":"pass","version":"v3.2.1","description":"Tyk GW","details":{"redis":{"status":"pass","componentType":"datastore","time":"2022-03-24T09:26:08Z"}}}`

Hierzu gibt es 3 Methoden:

i) Browser öffnen und auf <http://localhost:8080/hello> gehen.

ii) Curl verwenden. Hierzu in der Konsole `curl http://localhost:8080/hello` eingeben.

iii) Postman verwenden. Diese Methode wird auch für das restliche Tutorial verwendet. Wir stellen sicher, dass als Methode `GET` ausgewählt ist und geben als Request URL `http://localhost:8080/hello` ein. Einen Request Body brauchen wir in diesem Fall nicht. Anschließend drücken wir auf `Send`. Die Antwort sehen wir unten.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_Gateway_Check.jpg?raw=true)

5) API Secret `x-tyk-authorization` finden. Dieses befindet sich in `tyk.hybrid.conf` und ist standardmäßig `foo`.

# Erstellen eines APIs

Wir erstellen im Folgenden eine API für HelloJexxa. Die API für BookStoreJ wird analog dazu erstellt.
Zunächst benötigen wir die lokale IP Adresse. Hierfür geben wir in der Kommandozeile `ipconfig` ein.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Lokale_IP_Adresse_Finden.jpg?raw=true)

Die IPv4 Addresse ist die von unserem Rechner, die wir statt `localhost` bzw. `127.0.0.1` verwenden müssen.

1) API erstellen. Wichtig hierbei ist, dass die Methode auf `POST` gesetzt ist, die Adresse stimmt und die Proxyeinstellungen korrekt konfiguriert sind. `name`, `slug` und `api_id` sollten auch eingetragen werden. Der Port bei den Proxyeinstellungen ist der Port auf dem HelloJexxa im Tutorialstack läuft (7501).

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_New_API.jpg?raw=true)

<details> 
  <summary> JSON </summary> 
  
  ```javascript 
  {
    "name": "Hello Jexxa",
    "slug": "hellojexxa",
    "api_id": "Hello-Jexxa",
    "org_id": "1",
    "use_keyless": true,
    "auth": {
      "auth_header_name": "Authorization"
    },
    "definition": {
      "location": "header",
      "key": "x-api-version"
    },
    "version_data": {
      "not_versioned": true,
      "versions": {
        "Default": {
          "name": "Default",
          "use_extended_paths": true
        }
      }
    },
    "proxy": {
      "listen_path": "/hellojexxa/",
      "target_url": "http://{LOKALE_IP_ADRESSE}:7501/",
      "strip_listen_path": true
    },
    "active": true
}
  ```
</details> 

2) API Secret hinzufügen. Um ein neues API zu erstellen, muss man sein API Secret mitgeben. Hierzu gehen wir auf `Headers` und fügen unser API Secret hinzu. Falls bereits mehrere Felder angezeigt werden, können diese auch versteckt werden. Anschließend wird auf `Send` gedrückt. Dieses API Secret muss bei jeder Anfrage an das API Gateway hinzugefügt werden, die dieses verändern.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_New_API_Auth.jpg?raw=true)

3) APIs Aktualisieren. Damit dieses API auch übernommen wird, muss erst aktualisiert werden. Hierzu senden wir eine `GET` Anfrage an `http://localhost:8080/tyk/reload/group`. Hier muss auch das API Secret angegeben werden.

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_Refresh_APIs.jpg?raw=true)

# Überprüfen des APIs

Wir überprüfen unser API für HelloJexxa. Normalerweise würden wir als Pfad `http://localhost:7501/HelloJexxa/greetings` eingeben. Da wir aber nun über das API Gateway darauf zugreifen wollen benutzen wir nun den Pfad `http://localhost:8080/hellojexxa/HelloJexxa/greetings`. Für eine Erklärung siehe folgendes Bild:

![Bild](https://github.com/paul-priv/jexxa-tyk/blob/main/Screenshots/Postman_HelloJexxa_Check.jpg?raw=true)

Da wir nichts an dem API Gateway in diesem Schritt verändern, brauchen wir hierfür nicht das API Secret. Durch das drücken auf `Send` sollte nun als Ausgabe `Hello Jexxa` erscheinen.

