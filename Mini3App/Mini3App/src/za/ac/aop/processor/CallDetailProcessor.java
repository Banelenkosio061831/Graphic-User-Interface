package za.ac.aop.processor;

import java.util.ArrayList;

public class CallDetailProcessor {
    private ArrayList<DetailRecord> records;

    public CallDetailProcessor() {
        records = new ArrayList<>();
    }

    public void addDR(DetailRecord record) {
        records.add(record);
    }

    public ArrayList<DetailRecord> getRecords() {
        return records;
    }

    public void clearRecords() {
        records.clear();
    }
}
