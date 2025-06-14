#!/bin/bash

# Setup Cronjob that executes spark-batch-submit.sh daily at 2:00 AM

SCRIPT_PATH="$1"
LOG_PATH="${2:-/tmp/spark-batch.log}"

if [ -z "$SCRIPT_PATH" ]; then
  echo "Usage: $0 /path/to/scripts/spark-batch-submit.sh [/path/to/logs/spark-batch.log]"
  exit 1
fi

(crontab -l 2>/dev/null | grep -Fv "$SCRIPT_PATH" ; echo "0 2 * * * $SCRIPT_PATH >> $LOG_PATH 2>&1") | crontab -