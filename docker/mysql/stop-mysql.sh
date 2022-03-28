#!/usr/bin/env bash

NAME=${1:-default}-mysql
DATABASE=${2:-christophe}

PW=pw1234
USER_DB=christophe

DB_USER_PW="-u\"${USER_DB}\" -p\"${PW}\""

docker exec "${NAME}" sh -c "exec mysqldump --no-tablespaces ${DB_USER_PW} ${DATABASE}" > ./"${NAME}-${DATABASE}".sql

docker ps -aq -f "name=${NAME}" | while read l; do docker rm -f "${l}"; echo "stopping ${l} - ${1}"; done