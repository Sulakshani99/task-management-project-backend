#!/usr/bin/env bash
# wait-for-it.sh: wait for host:port before running command

set -e

TIMEOUT=60
QUIET=0
WAITFORIT_CMDNAME=${0##*/}

usage() {
	cat <<USAGE >&2
Usage:
	$WAITFORIT_CMDNAME host:port [-t timeout] [-q] [-- command args]
USAGE
	exit 1
}

wait_for() {
	local wait_host="$1"
	local wait_port="$2"
	local start_ts
	start_ts=$(date +%s)

	while true; do
		(echo > "/dev/tcp/${wait_host}/${wait_port}") >/dev/null 2>&1 && break

		local now_ts
		now_ts=$(date +%s)
		local elapsed=$((now_ts - start_ts))
		if [ "$elapsed" -ge "$TIMEOUT" ]; then
			echo "$WAITFORIT_CMDNAME: timeout after ${TIMEOUT}s waiting for ${wait_host}:${wait_port}" >&2
			exit 1
		fi

		if [ "$QUIET" -eq 0 ]; then
			echo "$WAITFORIT_CMDNAME: waiting for ${wait_host}:${wait_port}..."
		fi
		sleep 1
	done

	if [ "$QUIET" -eq 0 ]; then
		echo "$WAITFORIT_CMDNAME: ${wait_host}:${wait_port} is available"
	fi
}

hostport=""
while [ "$#" -gt 0 ]; do
	case "$1" in
		*:*) hostport="$1"; shift ;;
		-t) TIMEOUT="$2"; shift 2 ;;
		-q) QUIET=1; shift ;;
		--) shift; break ;;
		*) usage ;;
	esac
done

if [ -z "$hostport" ]; then
	usage
fi

IFS=":" read -r host port <<EOF
$hostport
EOF

wait_for "$host" "$port"

if [ "$#" -eq 0 ]; then
	exit 0
fi

exec "$@"
