#!/usr/bin/env bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

usage() {
    echo "Usage: $0 -c [sequential | multithreaded | stream | skandium] -s size -i iterations -r repetitions -t nthreads";
    echo "       -c    specify computation type";
    echo "       -i    specify iterations";
    echo "       -r    specify number of test repetition";
    echo "       -s    specify universe size";
    echo "       -t    specify number of threads"; 1>&2; exit 1;
    }

while getopts ":c:s:i:r:f:t:" o; do
    case "${o}" in
        c)
            c=${OPTARG}
            ((c == "sequential" || c == "multithreaded" || c == "stream" || c == "skandium")) || usage
            ;;
        s)
            s=${OPTARG}
            ;;
        i)
            i=${OPTARG}
            ;;
        r)
            r=${OPTARG}
            ;;
        f)
            f=${OPTARG}
            ;;
        t)
            t=${OPTARG}
            ;;
        *)
            usage
            ;;
    esac
done
shift $((OPTIND-1))

if [ -z "${c}" ] || [ -z "${s}" ] || [ -z "${i}" ] || [ -z "${r}" ]; then
    usage
fi

if [ -z "${f}" ]; then
    f=/dev/stdout
fi

JAVA_PATH="${JAVA_HOME}"

if [ -z "${JAVA_PATH}" ]; then
    JAVA_PATH="java"
else
    JAVA_PATH+="/bin/java"
fi;

PROPERTIES_FILE="${CURRENT_DIR}/test.properties"
echo "ITERATIONS=${i}" > ${PROPERTIES_FILE}
echo "ROWS=${s}" >> ${PROPERTIES_FILE}
echo "COLUMNS=${s}" >> ${PROPERTIES_FILE}
echo "COMPUTATION=${c}" >> ${PROPERTIES_FILE}

if [ ! -z "${t}" ]; then
    echo "NTHREADS=${t}" >> ${PROPERTIES_FILE}
fi


TOTAL_TIME=0
for i in $(seq 1 ${r}); do
    O="`${JAVA_PATH} -jar ${CURRENT_DIR}/../target/GameOfLife-1.0.jar ${PROPERTIES_FILE}`"
    echo "${i}/${r} -> ${O}" >> ${f}
    TOTAL_TIME=$((${TOTAL_TIME}+${O}))
done

TOTAL_TIME=$((${TOTAL_TIME}/${r}))

echo "" >> ${f}
echo "Average -> ${TOTAL_TIME}" >> ${f}