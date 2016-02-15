docker:
	sbt assembly
	docker build -t pierone.stups.zalan.do/mop-taskforce/trout:latest-SNAPSHOT .

docker-push: docker
	docker push pierone.stups.zalan.do/mop-taskforce/trout:latest-SNAPSHOT

docker-run:
	docker run -p 8080:8080 -t pierone.stups.zalan.do/mop-taskforce/trout:latest-SNAPSHOT

tick:
	senza create senza.yaml tick latest-SNAPSHOT

tock:
	senza create senza.yaml tock latest-SNAPSHOT
