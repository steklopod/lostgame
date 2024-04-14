curl -X POST --location "http://localhost:8080/open-api-games/v1/games-processor" \
    -H "Content-Type: application/json" \
    -H "Sign: 71a972808b252c0f0895d1f5296bb3f5" \
    -d '{
          "api": "debit",
          "data": {
            "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
            "transactionId": "66cf56af-16e0-4387-8608-c2b4eeef4c00",
            "amount": 100,
            "currency": "BTC"
          }
        }'

