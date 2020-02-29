FROM java:8

WORKDIR /

ADD build/libs/news-parser-1.3.jar parser.jar

COPY ./entrypoint.sh /usr/bin/entrypoint.sh
ENTRYPOINT [ "entrypoint.sh" ]

CMD java -jar parser.jar
