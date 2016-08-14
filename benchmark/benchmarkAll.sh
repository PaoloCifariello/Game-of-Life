#!/usr/bin/env bash

COMPUTATIONS=("sequential" "multithreaded" "stream" "skandium")
DEFAULT_SIZE=1000
DEFAULT_ITERATIONS=500
DEFAULT_REPETITIONS=5

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"


while getopts ":s:i:r:t:" o; do
    case "${o}" in
        s)
            size=${OPTARG}
            ;;
        i)
            iterations=${OPTARG}
            ;;
        r)
            repetitions=${OPTARG}
            ;;
        t)
            nthreads=${OPTARG}
            ;;
    esac
done
shift $((OPTIND-1))

if [ -z "${size}" ]; then
    size=${DEFAULT_SIZE}
fi

if [ -z "${iterations}" ]; then
    iterations=${DEFAULT_ITERATIONS}
fi

if [ -z "${repetitions}" ]; then
    repetitions=${DEFAULT_REPETITIONS}
fi

FILE=${CURRENT_DIR}/results/$(date +'%d-%m-%Y_%H-%M-%S').txt

echo "Test executed on $(date +'%d/%m/%Y')" > ${FILE}
echo "" >> ${FILE}
echo "SIZE: ${size} x ${size}" >> ${FILE}
echo "ITERATIONS: ${iterations}" >> ${FILE}
echo "REPETITIONS: ${repetitions}" >> ${FILE}

if [ ! -z "${nthreads}" ]; then
    echo "NTHREADS: ${nthreads}" >> ${FILE}
fi

echo "" >> ${FILE}

for computation in "${COMPUTATIONS[@]}"; do
    echo "Benchmark with ${computation}" >> ${FILE}
    echo "" >> ${FILE}

    if [ -z "${nthreads}" ]; then
        ${CURRENT_DIR}/benchmark.sh -c ${computation} -s ${size} -i ${iterations} -r ${repetitions} -f ${FILE}
    else
        ${CURRENT_DIR}/benchmark.sh -c ${computation} -s ${size} -i ${iterations} -r ${repetitions} -f ${FILE} -t ${nthreads}
    fi

    echo "" >> ${FILE}
done
