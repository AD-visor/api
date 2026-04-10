CONNECT_URL="http://connect:8083"
CONNECTOR_NAME="outbox-connector"

echo "Debezium Connect 기동 대기 중..."
until curl -s -o /dev/null -w "%{http_code}" "$CONNECT_URL/connectors" | grep -q "200"; do
  echo "Connect 아직 미준비, 3초 후 재시도..."
  sleep 3
done

echo "Connect 준비 완료"

EXISTING=$(curl -s "$CONNECT_URL/connectors/$CONNECTOR_NAME")
if echo "$EXISTING" | grep -q "\"name\""; then
  echo "커넥터 이미 등록됨, 스킵"
  exit 0
fi

echo "커넥터 등록 중..."
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$CONNECT_URL/connectors" \
  -H "Content-Type: application/json" \
  -d @/config/outbox-connector.json)

if [ "$RESPONSE" = "201" ]; then
  echo "커넥터 등록 성공"
else
  echo "커넥터 등록 실패: HTTP $RESPONSE"
  exit 1
fi
