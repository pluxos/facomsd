#!/usr/bin/env expect
spawn sh RunJavaJar.sh
expect "Welcome! Sometimes, help can help you =D\r"
sleep 1
send -raw "create 1 marcus\r"
sleep 1
send -raw "create 2 adriano\r"
sleep 1
send -raw "create 3 ferreira\r"
sleep 1
send -raw "create 4 pereira\r"
sleep 1
send -raw "create 1 marcus\r"
sleep 1
send -raw "read 1\r"
sleep 1
send -raw "read 2\r"
sleep 1
send -raw "dalete 4\r"
sleep 1
send -raw "update 4 adriano\r"
sleep 1
send -raw "exit\r"
interact