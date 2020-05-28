package com.overbond.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Builder
public class OutPutData {
    @JsonProperty("corporate_bond_id")
    private String corporateBondId;
    @JsonProperty("government_bond_id")
    private String governmentBondId;
    @JsonProperty("spread_to_benchmark")
    private String spreadToBenchMark;
}
