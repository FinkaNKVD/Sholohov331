/**
 * Перечисление уровней клиентов с их характеристиками
 */
public enum ClientLevel {
    BASIC("Базовый", 100.0) {
        @Override
        public double calculateCashback(double paymentAmount) {
            return paymentAmount >= 10000 ? paymentAmount * 0.01 : 0.0;
        }
    },

    PREMIUM("Премиум", 0.0) {
        @Override
        public double calculateCashback(double paymentAmount) {
            return paymentAmount >= 10000 ? paymentAmount * 0.05 : 0.0;
        }
    },

    VIP("ВИП", 0.0) {
        @Override
        public double calculateCashback(double paymentAmount) {
            if (paymentAmount >= 100000) {
                return paymentAmount * 0.10;
            } else if (paymentAmount >= 10000) {
                return paymentAmount * 0.05;
            } else {
                return paymentAmount * 0.01;
            }
        }
    };

    private final String description;
    private final double monthlyFee;

    ClientLevel(String description, double monthlyFee) {
        this.description = description;
        this.monthlyFee = monthlyFee;
    }

    public String getDescription() {
        return description;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    // Абстрактный метод для расчета кешбека
    public abstract double calculateCashback(double paymentAmount);
}