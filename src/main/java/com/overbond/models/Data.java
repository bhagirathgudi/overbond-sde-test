package com.overbond.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("tenor")
    private String tenor;
    @JsonProperty("yield")
    private String yield;
    @JsonProperty("amount_outstanding")
    private Long amountWithStanding;
}
