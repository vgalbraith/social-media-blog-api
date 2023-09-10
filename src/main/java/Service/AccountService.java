package Service;

import Model.Account;
import DAO.AccountDAO;

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
     * @return The persisted Account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        if (account.getUsername() == "" || 
            account.getPassword().length() < 4 ||
            accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        } else {
            return accountDAO.insertAccount(account);
        }
    }
}
