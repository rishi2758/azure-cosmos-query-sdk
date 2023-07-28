package com.azure.cosmos;

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
	private Predicate.Operator operator;
	private String[] values;
}
