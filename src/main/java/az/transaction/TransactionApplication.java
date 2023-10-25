package az.transaction;

import az.transaction.model.Account;
import az.transaction.repository.AccountRepository;
import az.transaction.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Account from = accountRepository.findById(1L).get();
        Account to = accountRepository.findById(2L).get();
        accountService.transferProxy(2L, 1L, 60.0);
    }
}
