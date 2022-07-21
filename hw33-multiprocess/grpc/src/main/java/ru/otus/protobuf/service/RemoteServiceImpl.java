package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.ServerMessage;

import java.util.concurrent.TimeUnit;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void sendMessage(ClientMessage request, StreamObserver<ServerMessage> responseObserver) {
        for (int i = request.getFirstValue(); i < request.getLastValue(); i++) {
            responseObserver.onNext(ServerMessage.newBuilder().setValue(i).build());
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        responseObserver.onCompleted();
    }
}
