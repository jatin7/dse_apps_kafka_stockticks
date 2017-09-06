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
    private static String tableNameTickLast = keyspaceName + ".last_tick_data";

    private static final String INSERT_INTO_TICK = "Insert into " + tableNameTick + " (symbol,date,value) values (?,?,?);";
    private static final String INSERT_INTO_LAST_TICK = "Insert into " + tableNameTickLast + " (symbol,date,value) values (?,?,?);";

    private PreparedStatement insertStmtTick;
    private PreparedStatement insertStmtLastTick;

    public TickDataDao(String[] contactPoints) {

        Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();

        this.session = cluster.connect();

        this.insertStmtTick = session.prepare(INSERT_INTO_TICK);
        this.insertStmtTick.setConsistencyLevel(ConsistencyLevel.ONE);

        this.insertStmtLastTick = session.prepare(INSERT_INTO_LAST_TICK);
        this.insertStmtLastTick.setConsistencyLevel(ConsistencyLevel.ONE);
    }

    public void insertTickData(TickData tickData) throws Exception{
        BoundStatement boundStmt = new BoundStatement(this.insertStmtTick);
        BoundStatement boundLastStmt = new BoundStatement(this.insertStmtLastTick);

        DateTime dateTime = DateTime.now();
        String month = fillNumber(dateTime.getMonthOfYear());
        String day = fillNumber(dateTime.getDayOfMonth());

        // changed to use symbol only. 09-05-17 Alex
        // String symbolWithDate = tickData.getKey() + "-" + dateTime.getYear() + "-" + month + "-" + day;
        String symbolWithDate = tickData.getKey() ;

        boundStmt.setString("symbol", symbolWithDate);
        boundStmt.setTimestamp("date", dateTime.toDate());
        boundStmt.setDouble("value", tickData.getValue());

        boundLastStmt.setString("symbol", symbolWithDate);
        boundLastStmt.setTimestamp("date", dateTime.toDate());
        boundLastStmt.setDouble("value", tickData.getValue());

        session.execute(boundStmt);
        session.execute(boundLastStmt);

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
