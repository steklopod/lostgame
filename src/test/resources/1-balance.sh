### BALANCE for gamer 1 (All requests are for 'PlayerOne')
curl -X POST --location "http://localhost:8080/open-api-games/v1/games-processor" \
    -H "Content-Type: application/json" \
    -H "Sign: 0bd78275be4ccb549f5c5736f2d5ed7e" \
    -d '{
          "api": "balance",
          "data": {
            "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
            "currency": "BTC"
          }
        }'
