package com.azure.cosmos.query;

import com.azure.cosmos.query.Predicate.Operator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CosmosFilter<T> {
    private T filter;
    private Operator operator;
    private String[] values;
}
