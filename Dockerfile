FROM python:3.6.7

RUN mkdir /workspace
RUN mkdir /workspace/server
#COPY client /workspace/client
#COPY server /workspace/server
#COPY config.py /workspace
#COPY grpcDefinitions/ /workspace/grpcDefinitions
COPY requirements.txt /workspace

WORKDIR /workspace

RUN pip3 install -r requirements.txt

WORKDIR /workspace/server
#CMD ["python3", "__init__.py", ${M}, ${N}]
