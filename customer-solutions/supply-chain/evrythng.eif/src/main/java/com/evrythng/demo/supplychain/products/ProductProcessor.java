package com.evrythng.demo.supplychain.products;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;

import java.util.Optional;

/**
 * Convert gs1 Product -> evrythng.Product
 */
public class ProductProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Optional<org.schema.Product> skuInfo = Optional.ofNullable(exchange.getIn().getBody(org.schema.Product.class));
        if (skuInfo.isPresent()) {
            TransformGS1ProductToEVTProduct transformProduct = new TransformGS1ProductToEVTProduct();
            Message message = new DefaultMessage();
            message.setBody(transformProduct.convert(skuInfo.get()));
            exchange.setOut(message);
        } else {
            throw new IllegalArgumentException("Not a SKU");
        }
    }
}