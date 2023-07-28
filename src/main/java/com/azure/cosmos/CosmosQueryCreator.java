package com.azure.cosmos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.MultiValuedMap;

@Getter
@AllArgsConstructor
public abstract class CosmosQueryCreator<T extends BaseFilter> {
	private final QueryParser<T> queryParser;
	public abstract String toCosmosQuery(MultiValuedMap<String, String> filters);
	public abstract String toCosmosPagedQuery(MultiValuedMap<String, String> filters, int page);
}
