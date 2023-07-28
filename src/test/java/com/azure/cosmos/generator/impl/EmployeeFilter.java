package com.azure.cosmos.generator.impl;

import com.azure.cosmos.BaseFilter;
import com.azure.cosmos.FilterFactory;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class EmployeeFilter implements FilterFactory<EmployeeFilter.Filter> {

	@Override
	public EmployeeFilter.Filter getFilter(String uiKey) {
		return Arrays.stream(Filter.values()).filter(filter -> filter.uiKey.equals(uiKey))
				.findFirst()
				.orElse(null);
	}

	@AllArgsConstructor
	public enum Filter implements BaseFilter {
		EMP_NAME("name","dbName");
		private final String uiKey;
		private final String dbKey;
		@Override
		public String getFilterKey() {
			return this.dbKey;
		}
		@Override
		public String getUiKey() {
			return this.uiKey;
		}
	}
}
