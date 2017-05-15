#/bin/bash

javac -cp .:krypto.jar -d bin/ src/*.java o3/*.java

cp krypto.jar bin/krypto.jar

cd bin 

java -cp .:krypto.jar Hovedprogram

cp bin/filename.txt ./
