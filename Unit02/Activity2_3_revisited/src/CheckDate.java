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
    public boolean validateDate(int day, int month, int year) {
        boolean leapYear = isLeapYear(year);
        if (day >= 1 && day <= 31) {
            if (month >= 1 && month <= 12) {
                if ((month == 6 || month == 4 || month == 9 || month == 11) && day == 31) {
                    throw new IllegalArgumentException("The month entered only has 30 days.");
                } else if (month == 2 && this.day > 29) {
                    if (!leapYear) {
                        throw new IllegalArgumentException("February (month 2) cannot have a day greater than 29 if it's not a leap year.");
                    }
                    throw new IllegalArgumentException("February (month 2) cannot have a day greater than 28.");
                } else {
                    return true;
                }
            }
        }
        else {
            return false;
        }
        return false;
    }
    public void setDay(int day) {
        if (day >= 1 && day <= maxDay)
        {
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
