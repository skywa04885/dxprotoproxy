package com.github.skywa04885.dxprotoproxy.server.modbus.slave;

import com.digitalpetri.modbus.requests.*;
import com.digitalpetri.modbus.responses.*;
import com.digitalpetri.modbus.slave.ServiceRequestHandler;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusRequestFactory;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusResponseFactory;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessage;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;

public class ModbusSlaveServiceHandler implements ServiceRequestHandler {
    private final @NotNull ArrayBlockingQueue<EnqueuedRequest> enqueuedThingies;

    public ModbusSlaveServiceHandler(@NotNull ArrayBlockingQueue<EnqueuedRequest> enqueuedThingies) {
        this.enqueuedThingies = enqueuedThingies;
    }

    /**
     * Handles the given service request (needed to merge the async code with the blocking code).
     * @param serviceRequest the service request to handle.
     * @return the completable future containing the response to send.
     */
    private @NotNull CompletableFuture<ModbusResponse> handleServiceRequest(@NotNull ServiceRequest<?, ?> serviceRequest) {
        // Create the modbus proxy request message from the modbus request.
        final @NotNull ModbusRequestMessage modbusRequestMessage =
                ModbusRequestFactory.create(0, serviceRequest.getRequest());

        // Create the request to enqueue.
        final EnqueuedRequest enqueuedRequest = new EnqueuedRequest(new CompletableFuture<>(), modbusRequestMessage);

        // Enqueue the request.
        try {
            enqueuedThingies.put(enqueuedRequest);
        } catch (@NotNull InterruptedException ignored) {}

        // Wait for the modbus proxy response to be received then turn it into a modbus response.
        return enqueuedRequest.completableFuture().thenApply(ModbusResponseFactory::create);
    }

    @Override
    public void onReadHoldingRegisters(ServiceRequest<ReadHoldingRegistersRequest,
            ReadHoldingRegistersResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((ReadHoldingRegistersResponse) modbusResponse));
    }

    @Override
    public void onReadInputRegisters(ServiceRequest<ReadInputRegistersRequest, ReadInputRegistersResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((ReadInputRegistersResponse) modbusResponse));
    }

    @Override
    public void onReadCoils(ServiceRequest<ReadCoilsRequest, ReadCoilsResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((ReadCoilsResponse) modbusResponse));
    }

    @Override
    public void onReadDiscreteInputs(ServiceRequest<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((ReadDiscreteInputsResponse) modbusResponse));
    }

    @Override
    public void onWriteSingleCoil(ServiceRequest<WriteSingleCoilRequest, WriteSingleCoilResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((WriteSingleCoilResponse) modbusResponse));
    }

    @Override
    public void onWriteSingleRegister(ServiceRequest<WriteSingleRegisterRequest, WriteSingleRegisterResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((WriteSingleRegisterResponse) modbusResponse));
    }

    @Override
    public void onWriteMultipleCoils(ServiceRequest<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((WriteMultipleCoilsResponse) modbusResponse));
    }

    @Override
    public void onWriteMultipleRegisters(ServiceRequest<WriteMultipleRegistersRequest,
            WriteMultipleRegistersResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((WriteMultipleRegistersResponse) modbusResponse));
    }

    @Override
    public void onMaskWriteRegister(ServiceRequest<MaskWriteRegisterRequest, MaskWriteRegisterResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((MaskWriteRegisterResponse) modbusResponse));
    }

    @Override
    public void onReadWriteMultipleRegisters(ServiceRequest<ReadWriteMultipleRegistersRequest,
            ReadWriteMultipleRegistersResponse> service) {
        handleServiceRequest(service).thenAccept(modbusResponse ->
                service.sendResponse((ReadWriteMultipleRegistersResponse) modbusResponse));
    }
}
