package com.azure.cosmos.generator.impl;

import com.azure.cosmos.AbstractQueryParser;
import com.azure.cosmos.CosmosFilter;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeQueryParser extends AbstractQueryParser<EmployeeFilter.Filter> {
    private final EmployeeFilter filter;
    public EmployeeQueryParser(EmployeeFilter filter) {
        this.filter = filter;
    }
    @Override
    public List<CosmosFilter<EmployeeFilter.Filter>> parse(MultiValuedMap<String, String> filters) {
        return filters.asMap().entrySet().stream()
                .map(entry -> entry.getValue()
                        .stream()
                        .map(query -> parseOperatorAndValue(filter.getFilter(entry.getKey()), query))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
