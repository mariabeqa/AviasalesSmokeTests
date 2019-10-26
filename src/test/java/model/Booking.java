package model;

public class Booking {
    private String whereTo;
    private String from;
    private int adultsAmount;
    private int kidsAmount;
    private String serviceClass;

    public boolean hasBuggage() {
        return hasBuggage;
    }

    public Booking withBuggage(boolean hasBuggage) {
        this.hasBuggage = hasBuggage;
        return this;
    }

    private boolean hasBuggage;

    public int getTransfersAmount() {
        return transfersAmount;
    }

    public Booking withTransfersAmount(int transfersAmount) {
        this.transfersAmount = transfersAmount;
        return this;
    }

    private int transfersAmount;

    public int getDuration() {
        return duration;
    }

    public Booking withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    private int duration;

    public String getWhereTo() {
        return whereTo;
    }

    public Booking withWhereTo(String where) {
        this.whereTo = where;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Booking withFrom(String from) {
        this.from = from;
        return this;
    }

    public int getAdultsAmount() {
        return adultsAmount;
    }

    public Booking withAdultsAmount(int adultsAmount) {
        this.adultsAmount = adultsAmount;
        return this;
    }

    public int getKidsAmount() {
        return kidsAmount;
    }

    public Booking withKidsAmount(int kidsAmount) {
        this.kidsAmount = kidsAmount;
        return this;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public Booking withServiceClass(String comfortLevel) {
        this.serviceClass = comfortLevel;
        return this;
    }

}
