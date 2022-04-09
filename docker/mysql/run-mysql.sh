#!/usr/bin/env bash

NAME=${1:-default}-mysql
PORT=${2:-5432}
DATABASE=${3:-christophe}

PW=pw1234
USER_DB=christophe

SCRIPT_DIR=$(dirname "$0")

DB_USER_PW="-u\"${USER_DB}\" -p\"${PW}\""

docker ps -aq -f "name=${NAME}" | while read l; do docker rm -f "${l}"; echo "stopping ${l} - ${1}"; done

docker run --name "${NAME}" \
  -p "${PORT}":3306 \
  -e MYSQL_ROOT_PASSWORD="${PW}" \
  -e MYSQL_DATABASE="${DATABASE}" \
  -e MYSQL_USER="${USER_DB}" \
  -e MYSQL_PASSWORD="${PW}" \
  -d mysql:latest \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci

while ! docker exec -i "${NAME}" sh -c "exec mysql ${DB_USER_PW} -e '\q';" ; do
  echo 'Wait until mysql is up';
  sleep 3s;
done

sleep 3s;

docker exec -i "${NAME}" sh -c "exec mysql ${DB_USER_PW} ${DATABASE}" < "${SCRIPT_DIR}/${NAME}-${DATABASE}".sql