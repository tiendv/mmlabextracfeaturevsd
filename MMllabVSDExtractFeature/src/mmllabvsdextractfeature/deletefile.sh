#!/bin/bash
# Log starting time /media/Hdhesu2/DataVulq
date

echo $1
echo $2
cd $1
tar -xvf /$1 -C /$2

# Log ending time
date 
