FROM python:3.6.7

RUN mkdir /workspace
RUN mkdir /workspace/server
#COPY client /workspace/client
#COPY server /workspace/server
#COPY config.py /workspace

WORKDIR /workspace/server

RUN pip3 install -r requirements.txt

CMD ['python3', '__init__.py']
