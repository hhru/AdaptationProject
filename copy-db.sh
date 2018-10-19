#!/bin/bash

pg_dump -h postgres-prod -F c $POSTGRES_DB | pg_restore -c -d $POSTGRES_DB
psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<EOF
    UPDATE personal_info SET email = '$ADAPT_MOCK_EMAIL';
EOF

exit 0
