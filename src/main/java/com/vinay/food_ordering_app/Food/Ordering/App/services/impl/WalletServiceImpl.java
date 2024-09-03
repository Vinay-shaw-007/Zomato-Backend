package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.WalletEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.WalletRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public void createNewWallet(UserEntity user) {
        WalletEntity wallet = WalletEntity.builder()
                .balance(0.0)
                .user(user)
                .build();
        walletRepository.save(wallet);
    }

    @Override
    public WalletEntity getUserWallet(UserEntity user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for this user with id: "+user.getId()));
    }

    @Override
    public WalletEntity addMoneyToWallet(WalletEntity wallet, Double amount) {

        wallet.setBalance(wallet.getBalance() + amount);

        return walletRepository.save(wallet);
    }

    @Override
    public WalletEntity debitMoneyFromWallet(WalletEntity wallet, Double amount) {

        wallet.setBalance(wallet.getBalance() - amount);

        return walletRepository.save(wallet);
    }
}
