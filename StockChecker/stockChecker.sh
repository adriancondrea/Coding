#!/bin/bash

content=$(wget https://www.amd.com/en/direct-buy/ro -q -O -)
echo $content
