package com.azure.cosmos;

public interface FilterFactory<T extends BaseFilter> {
	T getFilter(String uiKey);
}
