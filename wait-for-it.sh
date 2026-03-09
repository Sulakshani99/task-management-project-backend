wait-for-it.sh
docker-compose.yml
Dockerfile
target/api.jar

##!/usr/bin/env bash
## wait-for-it.sh: wait for a host:port to be available
## Usage: ./wait-for-it.sh host:port [-- command args]
#
#set -e
#TIMEOUT=15
#QUIET=0
#WAITFORIT_cmdname=${0##*/}
#
#usage() {
#    cat << USAGE >&2
#Usage:
#    $WAITFORIT_cmdname host:port [-t timeout] [-- command args]
#    -t TIMEOUT  Timeout in seconds (default $TIMEOUT)
#    -q          Quiet mode
#USAGE
#    exit 1
#}
#
#wait_for() {
#    local wait_host=$1
#    local wait_port=$2
#    local start_ts=$(date +%s)
#    while :; do
#        (echo > /dev/tcp/$wait_host/$wait_port) >/dev/null 2>&1
#        result=$?
#        if [[ $result -eq 0 ]]; then
#            local end_ts=$(date +%s)
#            [[ $QUIET -eq 0 ]] && echo "$WAITFORIT_cmdname: $wait_host:$wait_port is available after $((end_ts - start_ts)) seconds"
#            break
#        fi
#        local now_ts=$(date +%s)
#        local elapsed=$((now_ts - start_ts))
#        [[ $elapsed -gt $TIMEOUT ]] && echo "$WAITFORIT_cmdname: timeout after $TIMEOUT seconds" && exit 1
#        [[ $QUIET -eq 0 ]] && echo "$WAITFORIT_cmdname: waiting for $wait_host:$wait_port..."
#        sleep 1
#    done
#    return $result
#}
#
#while [[ $# -gt 0 ]]; do
#    case "$1" in
#        *:* ) hostport=$1; shift 1;;
#        -t) TIMEOUT="$2"; shift 2;;
#        -q) QUIET=1; shift 1;;
#        --) shift; break;;
#        *) usage;;
#    esac
#done
#
#if [[ "$hostport" == "" ]]; then usage; fi
#IFS=":" read -r host port <<< "$hostport"
#wait_for "$host" "$port"
#exec "$@"
