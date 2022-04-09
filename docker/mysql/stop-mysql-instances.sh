#!/usr/bin/env bash

SCRIPT_DIR=$(dirname "$0")

sh "${SCRIPT_DIR}"/stop-mysql.sh ds1
sh "${SCRIPT_DIR}"/stop-mysql.sh ds2