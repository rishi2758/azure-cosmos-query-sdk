package com.azure.cosmos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.azure.cosmos.Predicate.Operator;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CosmosFilter<T extends BaseFilter> {
	private T filter;
	private Operator operator;
	private String[] values;
}
