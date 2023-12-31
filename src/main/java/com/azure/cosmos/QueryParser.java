package com.azure.cosmos;

import org.apache.commons.collections4.MultiValuedMap;

import java.util.List;
public interface QueryParser<T extends BaseFilter> {
	List<CosmosFilter<T>> parse(MultiValuedMap<String, String> filters);
}
