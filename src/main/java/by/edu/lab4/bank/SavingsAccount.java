package by.edu.lab4.bank;

/**
 * Накопительный банковский счёт с капитализацией и бонусной ставкой.
 */
public class SavingsAccount extends BankAccount {
    private final double minimumBalance;
    private final double bonusRate;
    private final boolean capitalization;

    /**
     * Создаёт накопительный счёт.
     *
     * @param accountNumber номер счёта
     * @param owner владелец
     * @param balance баланс
     * @param interestRate годовая ставка
     * @param minimumBalance минимальный остаток
     * @param bonusRate дополнительная ставка
     * @param capitalization включена ли капитализация
     */
    public SavingsAccount(String accountNumber, String owner, double balance,
                          double interestRate, double minimumBalance,
                          double bonusRate, boolean capitalization) {
        super(accountNumber, owner, balance, interestRate);
        if (minimumBalance < 0 || bonusRate < 0) {
            throw new IllegalArgumentException("Минимальный остаток и бонус не могут быть отрицательными.");
        }
        this.minimumBalance = minimumBalance;
        this.bonusRate = bonusRate;
        this.capitalization = capitalization;
    }

    /**
     * Рассчитывает месячные проценты с учётом бонуса при выполнении условия
     * о минимальном остатке.
     *
     * @return сумма процентов за месяц
     */
    @Override
    public double calculateMonthlyInterest() {
        if (balance >= 2 * minimumBalance) {
            return balance * ((interestRate + bonusRate) / 100.0) / 12.0;
        }
        return super.calculateMonthlyInterest();
    }


    /**
     * Применяет месячные проценты к балансу, если включена капитализация.
     */
    public void applyCapitalization() {
        if (capitalization) {
            balance += calculateMonthlyInterest();
        }
    }

    /**
     * Возвращает информацию о накопительном счёте.
     *
     * @return текст с параметрами и расчётом
     */
    @Override
    public String getInfo() {
        return "Тип счёта: накопительный"
                + "\nБаланс: " + String.format("%.2f", balance)
                + "\nМинимальный остаток: " + String.format("%.2f", minimumBalance)
                + "\nБонусная ставка: " + String.format("%.2f%%", bonusRate)
                + "\nКапитализация: " + (capitalization ? "включена" : "выключена")
                + String.format("\nПроценты за месяц: %.2f", calculateMonthlyInterest())
                + (balance >= 2 * minimumBalance ? "\nУсловие для бонуса выполнено." : "\nУсловие для бонуса не выполнено.");
    }
}
