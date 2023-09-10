package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * No-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Uses the AccountDAO to persist an Account. The given Account will not have an account_id provided.
     * @param account An Account object.
     * @return The persisted Account if the persistence is successful, otherwise null.
     */
    public Account addAccount(Account account) {
        if (account.getUsername().equals("") || 
            account.getPassword().length() < 4 ||
            accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        } else {
            return accountDAO.insertAccount(account);
        }
    }

    /**
     * Uses the AccountDAO to verify a login.
     * @param username The provided username.
     * @param password The provided password.
     * @return The matching Account if the login is valid, otherwise null.
     */
    public Account getAccountByLogin(String username, String password) {
        Account verifiedAccount = accountDAO.getAccountByUsername(username);
        if (verifiedAccount == null || !verifiedAccount.getPassword().equals(password)) {
            return null;
        }
        return verifiedAccount;
    }
}
