#!/bin/bash

echo "Waiting for Kafka Connect..."
sleep 10

echo "Registering connector..."

curl -X POST http://connect:8083/connectors \
  -H "Content-Type: application/json" \
  -d @/config/outbox-connector.json

echo "Connector registered!"
