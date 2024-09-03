package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.WalletEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

public interface WalletService {

    void createNewWallet(UserEntity user);

    WalletEntity getUserWallet(UserEntity user);

    WalletEntity addMoneyToWallet(WalletEntity wallet, Double amount);

    WalletEntity debitMoneyFromWallet(WalletEntity wallet, Double amount);
}
