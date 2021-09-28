package Activity2;

public class CheckDate {
    protected final int maxDay = 31;
    protected final int maxMonth = 12;
    protected int day;
    protected int month;
    protected int year;

    public CheckDate() {

    }

    public int getDay() {
        return day;
    }

    public boolean setDay(int day) {
        if (day <= maxDay && day >= 1) {
            this.day = day;
            return true;
        }
        else {
            return false;
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > maxMonth || month < 1) {
            throw new IllegalArgumentException("Value must be between the range 1-31.");
        } else if (month == 2 && this.day > 29) {
            throw new IllegalArgumentException("February (month 2) cannot have a day greater than 29.");
        } else {
            this.month = month;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isLeapYear(int year) {
        return year % 4 == 0;
    }
}

