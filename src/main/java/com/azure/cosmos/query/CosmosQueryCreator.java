package com.azure.cosmos.query;

import org.apache.commons.collections4.MultiValuedMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class CosmosQueryCreator<T> {

    @Getter
    private final QueryParser<T> queryParser;

    public abstract String toCosmosQuery(MultiValuedMap<String, String> filters);

    public abstract String toCosmosPagedQuery(MultiValuedMap<String, String> filters, int page);
}
