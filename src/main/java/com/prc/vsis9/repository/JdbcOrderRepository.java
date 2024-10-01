//package com.prc.vsis8.repository;
//
//import com.prc.vsis8.data.IngredientRef;
//import com.prc.vsis8.data.Shawerma;
//import com.prc.vsis8.data.ShawermaOrder;
//
//import org.springframework.asm.Type;
//import org.springframework.dao.IncorrectResultSizeDataAccessException;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository {
//    private JdbcOperations jdbcOperations;
//    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
//        this.jdbcOperations = jdbcOperations;
//    }
//    @Override
//    @Transactional
//    public ShawermaOrder save(ShawermaOrder order) {
//        PreparedStatementCreatorFactory pscf =
//                new PreparedStatementCreatorFactory(
//                        "insert into Shawerma_Order "
//                                + "(delivery_name, delivery_street, delivery_city, "
//                                + "delivery_state, delivery_zip, cc_number, "
//                                + "cc_expiration, cc_cvv, placed_at) "
//                                + "values (?,?,?,?,?,?,?,?,?)",
//                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
//                );
//        pscf.setReturnGeneratedKeys(true);
//        order.setPlacedAt(new Date());
//        PreparedStatementCreator psc =
//                pscf.newPreparedStatementCreator(
//                        Arrays.asList(
//                                order.getDeliveryName(),
//                                order.getDeliveryStreet(),
//                                order.getDeliveryCity(),
//                                order.getDeliveryState(),
//                                order.getDeliveryZip(),
//                                order.getCcNumber(),
//                                order.getCcExpiration(),
//                                order.getCcCVV(),
//                                order.getPlacedAt()));
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcOperations.update(psc, keyHolder);
//        long orderId = keyHolder.getKey().longValue();
//        order.setId(orderId);
//        List<Shawerma> shawermas = order.getShawermas();
//        int i=0;
//        for (Shawerma shawerma : shawermas) {
//            saveShawerma(orderId, i++, shawerma);
//        }
//        return order;
//    }
//    private long saveShawerma(Long orderId, int orderKey, Shawerma shawerma) {
//        shawerma.setCreatedAt(new Date());
//        PreparedStatementCreatorFactory pscf =
//                new PreparedStatementCreatorFactory(
//                        "insert into Shawerma "
//                                + "(name, created_at, shawarma_order, shawarma_order_key) "
//                                + "values (?, ?, ?, ?)",
//                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
//                );
//        pscf.setReturnGeneratedKeys(true);
//        PreparedStatementCreator psc =
//                pscf.newPreparedStatementCreator(
//                        Arrays.asList(
//                                shawerma.getName(),
//                                shawerma.getCreatedAt(),
//                                orderId,
//                                orderKey));
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcOperations.update(psc, keyHolder);
//        long shawermaId = keyHolder.getKey().longValue();
//        shawerma.setId(shawermaId);
//        saveIngredientRefs(shawermaId, shawerma.getIngredients());
//        return shawermaId;
//    }
//    private void saveIngredientRefs(
//            long shawermaId, List<IngredientRef> ingredientRefs) {
//        int key = 0;
//        for (IngredientRef ingredientRef : ingredientRefs) {
//            jdbcOperations.update(
//                    "insert into Ingredient_Ref (ingredient, shawerma, shawerma_key) "
//                            + "values (?, ?, ?)",
//                    ingredientRef.getIngredient(), shawermaId, key++);
//        }
//    }
//
//    public Optional<ShawermaOrder> findById(Long id) {
//        try {
//            ShawermaOrder order = jdbcOperations.queryForObject(
//                    "select id, delivery_name, delivery_street, delivery_city, "
//                            + "delivery_state, delivery_zip, cc_number, cc_expiration, "
//                            + "cc_cvv, placed_at from Shawerma_Order where id=?",
//                    (row, rowNum) -> {
//                        ShawermaOrder shawermaOrder = new ShawermaOrder();
//                        shawermaOrder.setId(row.getLong("id"));
//                        shawermaOrder.setDeliveryName(row.getString("delivery_name"));
//                        shawermaOrder.setDeliveryStreet(row.getString("delivery_street"));
//                        shawermaOrder.setDeliveryCity(row.getString("delivery_city"));
//                        shawermaOrder.setDeliveryState(row.getString("delivery_state"));
//                        shawermaOrder.setDeliveryZip(row.getString("delivery_zip"));
//                        shawermaOrder.setCcNumber(row.getString("cc_number"));
//                        shawermaOrder.setCcExpiration(row.getString("cc_expiration"));
//                        shawermaOrder.setCcCVV(row.getString("cc_cvv"));
//                        shawermaOrder.setPlacedAt(new Date(row.getTimestamp("placed_at").getTime()));
//                        shawermaOrder.setShawermas(findShawermasByOrderId(row.getLong("id")));
//                        return shawermaOrder;
//                    }, id);
//            return Optional.of(order);
//        } catch (IncorrectResultSizeDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//    private List<Shawerma> findShawermasByOrderId(long orderId) {
//        return jdbcOperations.query(
//                "select id, name, created_at from Shawerma "
//                        + "where shawerma_order=? order by shawerma_order_key",
//                (row, rowNum) -> {
//                    Shawerma shawerma = new Shawerma();
//                    shawerma.setId(row.getLong("id"));
//                    shawerma.setName(row.getString("name"));
//                    shawerma.setCreatedAt(new Date(row.getTimestamp("created_at").getTime()));
//                    shawerma.setIngredients(findIngredientsByShawermaId(row.getLong("id")));
//                    return shawerma;
//                },
//                orderId);
//    }
//    private List<IngredientRef> findIngredientsByShawermaId(long shawermaId) {
//        return jdbcOperations.query(
//                "select ingredient from Ingredient_Ref "
//                        + "where shawerma = ? order by shawerma_key",
//                (row, rowNum) -> {
//                    return new IngredientRef(row.getString("ingredient"));
//                },
//                shawermaId);
//    }
//
//}
