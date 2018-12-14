FROM python:2.7-jessie

RUN mkdir /workspace
RUN mkdir /workspace/server
#COPY client /workspace/client
#COPY server /workspace/server
#COPY config.py /workspace
#COPY grpcDefinitions/ /workspace/grpcDefinitions
COPY requirements.txt /workspace

WORKDIR /workspace

ENV PYTHONPATH /workspace/server

RUN pip install -r requirements.txt

RUN apt-get update && apt-get install net-tools

WORKDIR /workspace/server
#CMD ["python3", "__init__.py", ${M}, ${N}]
