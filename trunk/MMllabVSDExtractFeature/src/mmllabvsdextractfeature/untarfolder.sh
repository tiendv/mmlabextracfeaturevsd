#!/bin/bash
# Log starting time /media/Hdhesu2/DataVulq
date

#echo $1
#echo $2
mkdir -p /$2
tar -xvf /$1 -C /$2

# Log ending time
date 
