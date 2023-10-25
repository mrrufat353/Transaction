package az.transaction.service;

import az.transaction.model.Account;
import az.transaction.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    private final EntityManagerFactory entityManagerFactory;

    public void transferProxy(Long fromId, Long toId, Double amount) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Account from = em.find(Account.class, fromId);
            Account to = em.find(Account.class, toId);
            transfer(from, to, amount);
        } catch (Exception e) {
            em.getTransaction().rollback();
        }finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public void transfer(Account from, Account to, Double amount) throws Exception {
        if (from.getBalance() <= amount) {
            throw new RuntimeException("Balance not enough");
        }
        from.setBalance(from.getBalance() - amount);
        log.info("I'm waiting for 5 seconds, because internet connection is weak");
        to.setBalance(to.getBalance() + amount);
        Thread.sleep(5000);
    }
}
