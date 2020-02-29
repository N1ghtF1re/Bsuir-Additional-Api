FROM java:8

WORKDIR /

ADD build/libs/bot-1.3.jar bot.jar


COPY ./entrypoint.sh /usr/bin/entrypoint.sh
ENTRYPOINT [ "entrypoint.sh" ]

CMD java java -jar bot.jar
