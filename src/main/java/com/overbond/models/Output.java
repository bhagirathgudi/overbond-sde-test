package com.overbond.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Output {
    private List<OutPutData> data = new ArrayList<>();
}
