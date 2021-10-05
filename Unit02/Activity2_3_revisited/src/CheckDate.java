public class CheckDate {
    protected final int maxDay = 31;
    protected final int maxMonth = 12;
    protected int day;
    protected int month;
    protected int year;

    public CheckDate() {

    }
    public CheckDate(int day, int month, int year) {
        if (validateDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
        else {
            throw new IllegalArgumentException("Incorrect date.");
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean validateDate(int day, int month, int year) {
        boolean leapYear = isLeapYear(year);
        if (day < 1 || day > 31) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (month == 2) {
            if (leapYear && day < 29) {
                return false;
            }
            if (!leapYear && day > 28) {
                return false;
            }
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false;
        }
        return true;
    }
    public void setDay(int day) {
        if (validateDate(day, getMonth(), getYear())) {
            this.day = day;
        }
        else {
            throw new IllegalArgumentException("Day out of range.");
        }
    }
    public void setMonth(int month) {
        if (day >= 1 && day <= maxMonth)
        {
            this.month = month;
        }
        else {
            throw new IllegalArgumentException("Month out of range.");
        }
    }
    public boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0 && year % 100 == 0;
    }
}
