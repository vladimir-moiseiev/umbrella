package com.umbrella.model.internal;

import com.google.common.collect.Lists;
import com.umbrella.model.internal.entity.Provider;
import com.umbrella.model.internal.repository.ProviderRepository;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
@Transactional
public class DataCreator {

    public static final String TRIOLAN = "Триолан";
    public static final String VOLYA = "Воля";
    public static final String KYIVSTAR = "Киевстар";
    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    void create() {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                if (Lists.newArrayList(providerRepository.findAll()).size() == 0) {
                    providerRepository.save(new Provider(TRIOLAN));
                    providerRepository.save(new Provider(VOLYA));
                    providerRepository.save(new Provider(KYIVSTAR));
                }


            }
        });

    }
}
