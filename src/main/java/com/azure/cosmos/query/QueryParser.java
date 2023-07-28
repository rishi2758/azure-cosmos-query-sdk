package com.azure.cosmos.query;

import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;

public interface QueryParser<T> {

    List<CosmosFilter<T>> parse(MultiValuedMap<String, String> filters);
}
