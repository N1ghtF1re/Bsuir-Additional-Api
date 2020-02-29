FROM java:8

WORKDIR /

ADD build/libs/news-parser-1.3.jar parser.jar

COPY ./entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh

ENTRYPOINT [ "./entrypoint.sh" ]

CMD ["java", "-jar", "parser.jar"]
