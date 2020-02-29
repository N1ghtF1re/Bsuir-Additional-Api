#!/bin/bash

set -e

host="server"
port="8082"
cmd="$@"

>&2 echo "!!!!!!!! Check conteiner_a for available !!!!!!!!"

until curl http://"$host":"$port"; do
  >&2 echo "Conteiner_A is unavailable - sleeping"
  sleep 1
done

>&2 echo "Conteiner_A is up - executing command"

exec $cmd