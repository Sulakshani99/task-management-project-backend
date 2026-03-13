#!/usr/bin/env bash
# wait-for-mysql.sh - wait for MySQL to be ready
# Reads MYSQL_HOST and MYSQL_PORT from environment variables.

set -e

host="${MYSQL_HOST:-mysql}"
port="${MYSQL_PORT:-3306}"

echo "Waiting for MySQL at ${host}:${port} to be ready..."

# Use bash TCP check to avoid extra dependencies such as netcat.
until (echo > "/dev/tcp/${host}/${port}") >/dev/null 2>&1; do
  echo "MySQL is not ready yet - sleeping"
  sleep 2
done

echo "MySQL is up - executing command"

if [ "$#" -eq 0 ]; then
  exit 0
fi

exec "$@"
