package com.datastax.tickdata.consumer;

import com.datastax.driver.core.*;
import com.datastax.tickdata.utils.TickData;
import org.joda.time.DateTime;

import java.util.concurrent.atomic.AtomicLong;

public class TickDataDao {
    private AtomicLong TOTAL_POINTS = new AtomicLong(0);
    private Session session;
    private static String keyspaceName = "datastax_tickdata_demo";
    private static String tableNameTick = keyspaceName + ".tick_data";

    private static final String INSERT_INTO_TICK = "Insert into " + tableNameTick + " (symbol,date,value) values (?,?,?);";

    private PreparedStatement insertStmtTick;

    public TickDataDao(String[] contactPoints) {

        Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();

        this.session = cluster.connect();

        this.insertStmtTick = session.prepare(INSERT_INTO_TICK);
        this.insertStmtTick.setConsistencyLevel(ConsistencyLevel.ONE);
    }

    public void insertTickData(TickData tickData) throws Exception{
        BoundStatement boundStmt = new BoundStatement(this.insertStmtTick);

        DateTime dateTime = DateTime.now();
        String month = fillNumber(dateTime.getMonthOfYear());
        String day = fillNumber(dateTime.getDayOfMonth());

        String symbolWithDate = tickData.getKey() + "-" + dateTime.getYear() + "-" + month + "-" + day;

        boundStmt.setString("symbol", symbolWithDate);
        boundStmt.setTimestamp("date", dateTime.toDate());
        boundStmt.setDouble("value", tickData.getValue());

        session.execute(boundStmt);

        TOTAL_POINTS.incrementAndGet();

        return;
    }

    private String fillNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    public long getTotalPoints() {
        return TOTAL_POINTS.get();
    }
}
