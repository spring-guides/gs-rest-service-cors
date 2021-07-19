cd $(dirname $0)
cd ../complete
./mvnw clean package
java -jar target/rest-service-cors-complete-0.0.1-SNAPSHOT.jar &
PID=$!
sleep 15
curl -s http://localhost:8080/greeting > target/actual.json
curl -s http://localhost:8080/greeting-javaconfig > target/actual-javaconfig.json
kill -9 $PID

echo "Let's look at the actual results: `cat target/actual.json`"
echo "And compare it to: `cat ../test/expected-first.json`"

if diff -w ../test/expected-first.json target/actual.json
    then
        echo SUCCESS
        let ret=0
    else
        echo FAIL
        let ret=255
        exit $ret
fi

echo "Let's look at the actual results: `cat target/actual-javaconfig.json`"
echo "And compare it to: `cat ../test/expected-second.json`"

if diff -w ../test/expected-second.json target/actual-javaconfig.json
    then
        echo SUCCESS
        let ret=0
    else
        echo FAIL
        let ret=255
        exit $ret
fi

rm -rf target

./gradlew clean build
java -jar build/libs/rest-service-cors-0.0.1-SNAPSHOT.jar &
PID=$!
sleep 15
curl -s http://localhost:8080/greeting  > build/actual.json
curl -s http://localhost:8080/greeting-javaconfig  > build/actual-javaconfig.json
kill -9 $PID

echo "Let's look at the actual results: `cat build/actual.json`"
echo "And compare it to: `cat ../test/expected-first.json`"

if diff -w ../test/expected-first.json build/actual.json
    then
        echo SUCCESS
        let ret=0
    else
        echo FAIL
        let ret=255
        exit $ret
fi

echo "Let's look at the actual results: `cat build/actual-javaconfig.json`"
echo "And compare it to: `cat ../test/expected-second.json`"

if diff -w ../test/expected-second.json build/actual-javaconfig.json
    then
        echo SUCCESS
        let ret=0
    else
        echo FAIL
        let ret=255
        exit $ret
fi

rm -rf build

cd ../initial

./mvnw clean compile
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi
rm -rf target

./gradlew clean compileJava
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi
rm -rf build

exit
