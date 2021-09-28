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

    public boolean setMonth(int month) {
        if (month > maxMonth || month < 1) {
            this.month = Integer.parseInt(null);
            return false;
        }
        else if (month == 2 && this.day < 29) {
            this.month = Integer.parseInt(null);
            return false;
        }
        else {
            this.month = month;
            return true;
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

