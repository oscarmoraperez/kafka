package org.oka.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.oka.kafka.model.Order;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

@Slf4j
@Component
public class FutureCallback implements ListenableFutureCallback<SendResult<String, Object>> {

    @Override
    public void onSuccess(SendResult<String, Object> result) {
        if (null != result) {
            log.info(Optional.ofNullable(result.getProducerRecord()).map(ProducerRecord::value).toString());
        } else {
            log.info("result is null");
        }
    }

    @Override
    public void onFailure(Throwable ex) {
        log.info("Unable to send message: " + ex.getMessage());
    }
}
