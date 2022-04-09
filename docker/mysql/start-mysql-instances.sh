#!/usr/bin/env bash

SCRIPT_DIR=$(dirname "$0")

sh "${SCRIPT_DIR}"/run-mysql.sh ds1 4321
sh "${SCRIPT_DIR}"/run-mysql.sh ds2 4322