package com.azure.cosmos;//package com.azure.cosmos.query;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ProgrammeQueryCreator extends CosmosQueryCreator<ProgrammeFilter> {
//
//    public ProgrammeQueryCreator(ProgrammeQueryParser queryParser) {
//        super(queryParser);
//    }
//
//    @Override
//    public String toCosmosQuery(MultiValueMap<String, String> filters) {
//        List<CosmosFilter<ProgrammeFilter>> cosmosFilters = this.getQueryParser().parse(filters);
//        List<Predicate> predicates = cosmosFilters.stream()
//                .map(filter -> Predicate.builder().withCosmosFilter(filter).toPredicate())
//                .collect(Collectors.toList());
//        CosmosQueryBuilder builder = CosmosQuery.builder(BaseQuery.SELECT_QUERY);
//        if (!predicates.isEmpty()) {
//            builder.withCriteria(Criteria.WHERE);
//            builder.withPredicate(
//                    Predicate.and(predicates.toArray(new Predicate[predicates.size()])));
//        }
//        return builder
//                .withCriteria(Criteria.ORDER_BY).withPredicate(Predicate.builder()
//                        .withOperator(Operator.DESC, "lastUpdatedAt", "").toPredicate())
//                .build().toCosmosQuery();
//    }
//
//    @Override
//    public String toCosmosPagedQuery(MultiValueMap<String, String> filters, int page) {
//        List<CosmosFilter<ProgrammeFilter>> cosmosFilters = this.getQueryParser().parse(filters);
//        List<Predicate> predicates = cosmosFilters.stream()
//                .map(filter -> Predicate.builder().withCosmosFilter(filter).toPredicate())
//                .collect(Collectors.toList());
//        CosmosQueryBuilder builder = CosmosQuery.builder(BaseQuery.SELECT_QUERY);
//        if (!predicates.isEmpty()) {
//            builder.withCriteria(Criteria.WHERE);
//            builder.withPredicate(
//                    Predicate.and(predicates.toArray(new Predicate[predicates.size()])));
//        }
//        return builder.withCriteria(Criteria.ORDER_BY)
//                .withPredicate(Predicate.builder().withOperator(Operator.DESC, "lastUpdatedAt", "")
//                        .toPredicate())
//                .withPredicate(Predicate.builder()
//                        .withOperator(Operator.OFFSET, "offset",
//                                String.valueOf(page * CommonUtils.DEFAULT_PAGE_SIZE_PROGRAMME))
//                        .withOperator(Operator.LIMIT, "limit",
//                                String.valueOf(CommonUtils.DEFAULT_PAGE_SIZE_PROGRAMME))
//                        .toPredicate())
//                .build().toCosmosQuery();
//    }
//
//}
