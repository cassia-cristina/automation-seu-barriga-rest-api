package me.wcquino.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.wcquino.utils.DateTimeUtil;

@Builder
@Getter
@Setter
public class Movimentacoes {
    @JsonProperty("tipo")
    private String type;

    @JsonProperty("data_transacao") @Builder.Default
    private String transactionDate = DateTimeUtil.getCurrentDate();

    @JsonProperty("data_pagamento") @Builder.Default
    private String paymentDate = DateTimeUtil.getCurrentDate();

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("envolvido")
    private String involved;

    @JsonProperty("valor")
    private Float value;

    @JsonProperty("conta_id")
    private Integer accountId;

    private Boolean status;
}
