package by.edu.lab4.bank;

/**
 * Базовый класс банковского счёта.
 * Содержит основные данные счёта и выполняет расчёт месячных процентов.
 */
public class BankAccount {
    private final String accountNumber;
    private final String owner;
    protected double balance;
    protected final double interestRate;

    /**
     * Создаёт банковский счёт.
     *
     * @param accountNumber номер счёта
     * @param owner владелец счёта
     * @param balance текущий баланс
     * @param interestRate годовая процентная ставка в процентах
     */
    public BankAccount(String accountNumber, String owner, double balance, double interestRate) {
        if (accountNumber == null || accountNumber.isBlank()
                || owner == null || owner.isBlank()
                || balance < 0 || interestRate < 0) {
            throw new IllegalArgumentException("Проверьте введённые данные.");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    /**
     * Рассчитывает проценты за один месяц.
     *
     * @return сумма месячных процентов
     */
    public double calculateMonthlyInterest() {
        return balance * (interestRate / 100.0) / 12.0;
    }

    /**
     * Возвращает основную информацию о счёте.
     *
     * @return текст с данными счёта
     */
    public String getInfo() {
        return "Тип счёта: обычный банковский счёт"
                + "\nНомер счёта: " + accountNumber
                + "\nВладелец: " + owner
                + String.format("\nБаланс: %.2f", balance)
                + String.format("\nСтавка: %.2f%%", interestRate)
                + String.format("\nПроценты за месяц: %.2f", calculateMonthlyInterest());
    }

    /**
     * Возвращает текущий баланс.
     *
     * @return баланс счёта
     */
    public double getBalance() {
        return balance;
    }
}
