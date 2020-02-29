FROM java:8

WORKDIR /

ADD build/libs/bot-1.3.jar bot.jar


COPY ./entrypoint.sh entrypoint.sh

RUN chmod +x entrypoint.sh
ENTRYPOINT [ "./entrypoint.sh" ]

CMD ["java", "-jar", "bot.jar"]
