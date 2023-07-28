//package com.azure.cosmos.query;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ProgrammeQueryParser implements QueryParser<ProgrammeFilter> {
//
//    @Override
//    public List<CosmosFilter<ProgrammeFilter>> parse(MultiValueMap<String, String> filters) {
//        return filters.entrySet().stream()
//                .map(entry -> ProgrammeFilter.parse(entry.getKey())
//                        .map(filter -> entry.getValue().stream()
//                                .map(query -> parseOperatorAndValue(filter, query))
//                                .filter(ObjectUtils::isNotEmpty).collect(Collectors.toList()))
//                        .orElse(Collections.emptyList()))
//                .filter(ObjectUtils::isNotEmpty).flatMap(List::stream).collect(Collectors.toList());
//    }
//
//    private CosmosFilter<ProgrammeFilter> parseOperatorAndValue(ProgrammeFilter filter,
//            String query) {
//        if (ObjectUtils.isNotEmpty(query)) {
//            int openIndex = query.indexOf('(');
//            int closeIndex = query.lastIndexOf(')');
//            try {
//                Operator operator = Operator.valueOf(query.substring(0, openIndex).toUpperCase());
//                String[] values = query.substring(openIndex + 1, closeIndex).split(",");
//                CosmosFilter<ProgrammeFilter> cosmosFilter = new CosmosFilter<ProgrammeFilter>();
//                cosmosFilter.setFilter(filter);
//                cosmosFilter.setOperator(operator);
//                cosmosFilter.setValues(values);
//                return cosmosFilter;
//            } catch (Exception e) {
//                log.error("invalid/unsupported operator {}", query);
//            }
//        }
//        return null;
//    }
//
//}
