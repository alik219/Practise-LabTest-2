#!/usr/bin/env bash
set -e
cd -- "$(dirname -- "$0")"
java tools/PracticeRunner.java "$@"
