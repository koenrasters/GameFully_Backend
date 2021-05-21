package gamefully.service.repositories;

import gamefully.service.models.Transaction;
import gamefully.service.repositories.interfaces.ITransaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryMem implements ITransaction
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Transaction> getTransactions()
    {
        return fakeDataStore.getTransactionList();
    }

    public Transaction getTransaction(int id)
    {
        return fakeDataStore.getTransaction(id);
    }

    public List<Transaction> getTransactions(String searchTerm, String column)
    {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = fakeDataStore.getTransaction(1);
        transactions.add(transaction);
        return transactions;
    }

    public void createTransaction(Transaction transaction)
    {
        fakeDataStore.addTransaction(transaction);
    }

    public void deleteTransaction(int id)
    {
        fakeDataStore.deleteTransaction(id);
    }

    public boolean updateTransaction(Transaction transaction)
    {
        return fakeDataStore.updateTransaction(transaction);
    }
}
