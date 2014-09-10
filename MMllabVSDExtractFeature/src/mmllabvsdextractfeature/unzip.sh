#!/bin/bash
# Log starting time /media/Hdhesu2/DataVulq
date
echo $inputFileDisk
echo $outputFolder

tar -xvf /$inputFileDisk -C /$outputFolder

# Log ending time
date 
