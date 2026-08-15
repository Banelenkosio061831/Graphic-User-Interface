package za.ac.aop.processor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DetailRecord {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long duration;
    private String phoneNumber;
    private String provider;

    public DetailRecord() {
    }

    public DetailRecord(LocalDate date, LocalTime startTime, LocalTime endTime, 
                       long duration, String phoneNumber, String provider) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
    }

    // Constructor that parses a CSV line
    public DetailRecord(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid record format");
        }
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        this.date = LocalDate.parse(parts[0].trim(), dateFormatter);
        this.startTime = LocalTime.parse(parts[1].trim(), timeFormatter);
        this.endTime = LocalTime.parse(parts[2].trim(), timeFormatter);
        this.duration = Long.parseLong(parts[3].trim());
        this.phoneNumber = parts[4].trim();
        this.provider = parts[5].trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    // Convenience methods for GUI
    public String getTime() {
        return startTime.toString();
    }

    public String getNumber() {
        return phoneNumber;
    }
}
