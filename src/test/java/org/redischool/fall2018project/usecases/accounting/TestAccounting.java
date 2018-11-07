package org.redischool.fall2018project.usecases.accounting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestAccounting {

    private AccountService accountService = new AccountService();

    @Test
    void testCreateNewAccount() {
        accountService.createAccount("5555");

        assertThat(accountService.getAccount("5555").getId()).isEqualTo("5555");
    }

    @Test
    void TheBalanceOfEmptyAccountIsZero() {
        accountService.createAccount("5555");

        assertThat(accountService.getAccount("5555").getBalance()).isEqualTo(0.0);
    }

    @Test
    void WeCanPutMonyInTheAccount() {
        accountService.createAccount("4545");

        accountService.deposit("4545", 500.0);
        assertThat(accountService.getAccount("4545").getBalance()).isEqualTo(500.0);
    }
}
