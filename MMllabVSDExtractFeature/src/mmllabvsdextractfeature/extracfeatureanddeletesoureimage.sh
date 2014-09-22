#!/bin/bash
# Log starting time /media/Hdhesu2/DataVulq
date

#echo $1
#echo $2
#echo $3
#echo $4
# $1: GPU Device;
# $2: GPU Cuda path in overfeat folder;
# $3: Image for extract feature;
# $4: result extract feature file;
# 
export LD_LIBRARY_PATH=/usr/lib/openblas-base/:/usr/local/cuda-5.5/lib64/

#  set cuda device to run extraction;
export CUDA_VISIBLE_DEVICES=$1
cd $2
./overfeat_cuda -f $3>$4

# delete file  result extract feature file;
rm -r -f /$3
# Log ending time
date 
