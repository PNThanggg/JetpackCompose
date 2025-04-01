package jc.app.bank_pick.data

data class TransactionItem(
    val id: String,
    val amount: Double,
    val currency: String,
    val date: String,
    val description: String,
    val status: TransactionStatus,
    val type: TransactionType,
    val purpose: String
) {
    val isIncome: Boolean
        get() = type == TransactionType.INCOME

    val isExpense: Boolean
        get() = type == TransactionType.EXPENSE

    val isCompleted: Boolean
        get() = status == TransactionStatus.COMPLETED

    val isPending: Boolean
        get() = status == TransactionStatus.PENDING

    val isFailed: Boolean
        get() = status == TransactionStatus.FAILED

    val isCancelled: Boolean
        get() = status == TransactionStatus.CANCELLED
}

enum class TransactionType {
    INCOME, EXPENSE
}

enum class TransactionStatus {
    PENDING, COMPLETED, FAILED, CANCELLED
}