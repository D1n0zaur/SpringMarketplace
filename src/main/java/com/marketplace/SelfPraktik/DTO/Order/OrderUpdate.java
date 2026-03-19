package com.marketplace.SelfPraktik.DTO.Order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class OrderUpdate {
    private Optional<String> address = Optional.empty();
    private Optional<List<Long>> productsId = Optional.empty();
}
