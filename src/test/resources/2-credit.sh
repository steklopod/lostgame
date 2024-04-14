curl -X POST --location "http://localhost:8080/open-api-games/v1/games-processor" \
    -H "Content-Type: application/json" \
    -H "Sign: bc7d98cbdd1992a5e91b937ac4c550ba" \
    -d '{
          "api": "credit",
          "data": {
            "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
            "transactionId": "77cf56af-16e0-4387-8608-c2b4eeef4c11",
            "amount": 10,
            "currency": "BTC"
          }
        }'
