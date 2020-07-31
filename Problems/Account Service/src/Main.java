interface AccountService {
    /**
     * It finds an account by owner id
     * @param id owner unique identifier
     * @return account or null
     */
    Account findAccountByOwnerId(long id);
    /**
     * It count the number of account with balance > the given value
     * @param value
     * @return the number of accounts
     */
    long countAccountsWithBalanceGreaterThan(long value);
}

// Declare and implement your AccountServiceImpl here

class Account {

    private long id;
    private long balance;
    private User owner;

    public Account(long id, long balance, User owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
    }

    public long getId() { 
        return id; 
    }

    public long getBalance() { 
        return balance; 
    }

    public User getOwner() { 
        return owner; 
    }

    @Override
    public String toString() {
        return  "Owner " + getOwner().toString() + "\n" +
                "Balance \u2192 " + getBalance();
    }
}

class User {

    private long id;
    private String firstName;
    private String lastName;

    public User(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() { 
        return id; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public String getLastName() { 
        return lastName; 
    }

    @Override
    public String toString() {
        return "Id \u2192 " + getId() + "\n" +
                "First Name \u2192 " + getFirstName() + "\n" +
                "Last Name \u2192 " + getLastName();
    }
}

class AccountServiceImpl implements AccountService {

    private Account[] listOfAccounts;

    public AccountServiceImpl(Account[] listOfAccounts) {
        this.listOfAccounts = listOfAccounts.clone();
    }

    @Override
    public Account findAccountByOwnerId(long id) {
        if (listOfAccounts == null) {
            return null;
        } else {
            for (Account acc : listOfAccounts) {
                if (id == acc.getOwner().getId()) {
                    return acc;
                }
            }
        }
        return null;
    }

    @Override
    public long countAccountsWithBalanceGreaterThan(long value) {
        int accountsWithMoolasGreaterThan = 0;
        for (Account acc : listOfAccounts) {
            if (acc.getBalance() > value) {
                accountsWithMoolasGreaterThan++;
            }
        }
        return accountsWithMoolasGreaterThan;
    }

//    public static void main(String[] args) {
//        Account[] accounts = {new Account(1,10,new User(1, "Peter", "Piper")),
//                new Account(1,10,new User(111, "Peter", "Piper")),
//                new Account(2,11,new User(112, "Peter", "Pan")),
//                new Account(3,12,new User(113, "Mick E.", "Mouse")),
//                new Account(4,13,new User(114, "Donald", "Duck")),
//                new Account(5,14,new User(115, "Goofy", "Goofy")),
//                new Account(6,15,new User(116, "John", "Constantine")),
//                new Account(7,16,new User(117, "Clark", "Griswald")),
//                new Account(8,17,new User(118, "Clark", "Kent")),
//                new Account(9,18,new User(119, "Barry", "Allen")),
//                new Account(10,19,new User(120, "Wally", "West"))
//        };
//
//        AccountServiceImpl service = new AccountServiceImpl(accounts);
//        final Account accountByOwnerId = service.findAccountByOwnerId(10L);

//        System.out.println("Account Details");
//        System.out.println(accountByOwnerId.toString());

//    }
}
