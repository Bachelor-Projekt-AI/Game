build:
	./gradlew build

clean:
	./gradlew clean

jar:
	./gradlew jar

run:
	./gradlew run

test:
	./gradlew test

version:
	./gradlew version -PnewVersion=$(v)
