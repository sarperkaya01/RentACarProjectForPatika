package com.example.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

@Aspect
@Component
public class VehicleTransactionAspect {

    private static final Logger logger = LoggerFactory.getLogger(VehicleTransactionAspect.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @VehiclesTransactional anotasyonu ile işaretlenmiş metotların etrafında çalışır.
     * Bu metot, araçlarla ilgili CRUD operasyonları için özel transaction yönetimi sağlar.
     */
    @Around("@annotation(com.example.Utils.Annotations.VehiclesTransactional)")
    public Object manageVehicleTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint.getSignature().getName() -> Çalıştırılan metodun adını verir (örn: "createAutomobile")
        String methodName = joinPoint.getSignature().getName();
        
        EntityTransaction transaction = entityManager.getTransaction();
        
        if (transaction.isActive()) {
            logger.debug("Joining existing transaction for method: {}", methodName);
            return joinPoint.proceed();
        }

        try {
            transaction.begin();
            logger.info("--- Vehicle Transaction Started for method: {} ---", methodName);

            Object result = joinPoint.proceed();

            transaction.commit();
            logger.info("--- Vehicle Transaction Committed for method: {} ---", methodName);

            return result;
        } catch (Throwable e) {
            if (transaction.isActive()) {
                transaction.rollback();
                // e.getMessage() -> Hatanın mesajını loglar.
                logger.error("--- Vehicle Transaction Rolled Back for method: {} ---. Reason: {}", methodName, e.getMessage());
            }
            
            throw e;
        }
    }
}
