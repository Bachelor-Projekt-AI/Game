build:
	mvn compile -Dexec.mainClass="org.bachelorprojekt.Main"
clean:
	rm -r target/*
jar:
	mvn package
run:
	mvn compile exec:java -Dexec.mainClass="org.bachelorprojekt.Main"
test:
	mvn test
version:
	mvn versions:set -DnewVersion=$v && mvn versions:commit
