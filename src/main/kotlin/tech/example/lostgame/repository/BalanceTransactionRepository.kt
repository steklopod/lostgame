package tech.example.lostgame.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.example.lostgame.model.BalanceTransaction
import java.util.*

@Repository
interface BalanceTransactionRepository : JpaRepository<BalanceTransaction, UUID>
