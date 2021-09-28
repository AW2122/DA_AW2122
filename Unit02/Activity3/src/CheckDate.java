public class CheckDate {
    protected final int maxDay = 31;
    protected final int maxMonth = 12;
    protected int day;
    protected int month;
    protected int year;

    public CheckDate(int day, int month, int year) {
        boolean isLeapYear = year % 4 == 0;

        if (day <= maxDay && day >= 1) {
            if (month > maxMonth || month < 1) {
                throw new IllegalArgumentException("Value must be between the range 1-12.");

            } else if ((month == 3 || month == 6 || month == 9 || month == 11) && day == 31) {
                throw new IllegalArgumentException("The month entered only has 30 days.");

            } else if (month == 2 && this.day > 29) {
                if (!isLeapYear) {
                    throw new IllegalArgumentException("February (month 2) cannot have a day greater than 29 if it's not a leap year.");
                }
                throw new IllegalArgumentException("February (month 2) cannot have a day greater than 28.");

            } else {
                this.month = month;
                this.year = year;
                this.day = day;
            }
        } else {
            throw new IllegalArgumentException("Value must be between the range 1-31.");
        }
    }
}

   /* public boolean isLeapYear(int year) {
        return year % 4 == 0;
    }*/
