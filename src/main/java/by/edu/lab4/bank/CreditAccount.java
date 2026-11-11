package by.edu.lab4.bank;

/**
 * Кредитный счёт, для которого проценты рассчитываются от текущей задолженности.
 */
public class CreditAccount extends BankAccount {
    private final double debt;

    /**
     * Создаёт кредитный счёт.
     *
     * @param accountNumber номер счёта
     * @param owner владелец
     * @param balance баланс
     * @param interestRate годовая процентная ставка
     * @param debt текущая задолженность
     */
    public CreditAccount(String accountNumber, String owner, double balance,
                         double interestRate, double debt) {
        super(accountNumber, owner, balance, interestRate);
        if (debt < 0) {
            throw new IllegalArgumentException("Задолженность не может быть отрицательной.");
        }
        this.debt = debt;
    }

    /**
     * Рассчитывает месячные проценты от суммы задолженности.
     *
     * @return сумма процентов по кредиту за месяц
     */
    @Override
    public double calculateMonthlyInterest() {
        return debt * interestRate / 100.0 / 12.0;
    }


    /**
     * Возвращает информацию о кредитном счёте.
     *
     * @return текст с данными кредитного счёта
     */
    @Override
    public String getInfo() {
        return "Тип счёта: кредитный"
                + "\nБаланс: " + String.format("%.2f", balance)
                + "\nТекущая задолженность: " + String.format("%.2f", debt)
                + String.format("\nПроценты по задолженности за месяц: %.2f", calculateMonthlyInterest());
    }
}
