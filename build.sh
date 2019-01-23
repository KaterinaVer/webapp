#!/usr/bin/env bash
docker build -f Dockerfile -t webserver .
docker build -f Dockerfile1 -t postgresql .