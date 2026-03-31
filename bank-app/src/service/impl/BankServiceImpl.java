// service  --> Actual implementation of logic

package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRespository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//Gives the template for the functionality for all the sevices
// It is used for the future advancements
// You can implement that BankService interface across various platforms like mobile/web/app

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository= new AccountRepository();
    private final TransactionRepository transactionRepository= new TransactionRepository();
    private final CustomerRespository customerRespository= new CustomerRespository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();

        //create customer
        Customer c = new Customer(customerId,name,email);
        customerRespository.save(c);

        //CHANGE this code letter --> Current Account Size + 1 = AC11
        //String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber,customerId,(double) 0,accountType);

        //How to save ACCOUNT INFORMATION
        accountRepository.save(account);

        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account Not Found: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.DEPOSIT,
                account.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note);

        transactionRepository.add(transaction);

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account Not Found: " + accountNumber));

        if(account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Balance");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.WITHDRAW,
                account.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note);

        transactionRepository.add(transaction);

    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        if(fromAcc.equals(toAcc)){
            throw new RuntimeException("Cannot transfer to your own account");
        }

        // from and too are the object reference that point to that account object
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new RuntimeException("Account Not Found: " + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new RuntimeException("Account Not Found: " + toAcc));

        if(from.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Balance");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction fromTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.TRANSFER_OUT,
                from.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note);

        Transaction toTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.TRANSFER_IN,
                to.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note);

        transactionRepository.add(fromTransaction);
        transactionRepository.add(toTransaction);


    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
//         List<Account> result = new ArrayList<>();
//         for(Customer c: customerRespository.findAll()){
//             if(c.getName().toLowerCase().contains(query)){
//                 result.addAll(accountRepository.findByCustomerId(c.getId()));
//             }
//         }
//         result.sort(Comparator.comparing(Account::getAccountNumber));

         return customerRespository.findAll().stream()
                 .filter(c -> c.getName().toLowerCase().contains(query))
                 .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                 .sorted(Comparator.comparing(Account::getAccountNumber))
                 .collect(Collectors.toList());

//       return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        String accountNumber = String.format("AC%06d",size);
        return accountNumber;
    }
}
