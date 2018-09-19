#!/bin/bash

pg_dump -h postgres-prod -F c $POSTGRES_DB | pg_restore -c -d $POSTGRES_DB
exit 0
